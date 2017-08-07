#!/bin/sh


#######################
# ENVIRONMENT VARIABLES
#######################

## Set the sonar URL on local machine
SONAR_URL="http://localhost:9000"
SONAR_SCANNER="C:/Users/gemani/Desktop/InContact/SoftwareTools/sonar-scanner-3.0.3.778-windows/bin/sonar-scanner.bat"
JQ="C:/Users/gemani/jq-win64.exe"
##


#####################################
# Steps for sonar coverage on project
#####################################
#wget="C:\Users\gemani\wget-1.18-win64\\wget.exe"

#$wget https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-5.6.4.zip

#unzip sonarqube-5.6.4.zip
#mv sonarqube-5.6.4 ./sonar



## Use the sonar scanner and run coverage on the project
$SONAR_SCANNER -Dsonar.host.url=$SONAR_URL -Dsonar.projectKey=shop-registration -Dsonar.sources=src -Dsonar.projectName="shop-registration"
#-Dsonar.exclusions=**/build/**/*,**/TinyMCE/plugins/**/* 
#-Dsonar.projectVersion=0.1 
#-Dsonar.javascript.lcov.reportPaths=coverage/lcov.info || true


## Copy the report file generated to the project directory
cp .scannerwork/report-task.txt ./report-task.txt

## Open the report in console
cat report-task.txt

## Load all the varaibles and their values in the session
. ./report-task.txt

# echo "Calling API $ceTaskUrl  (Attempt $i)"
    # curl -s -X GET $ceTaskUrl > result.json
	# sleep 20
	# result=`cat result.json | $JQ '.task.status'`

	# #cat result.json | underscore select '.task .status'
	# #result = jq -r '.task.status'
    # #result=`cat result.json | jq -r '.task.status'`
    # echo "Result = $result"
	
i=30
while [ "$i" -lt 32 ]; do
    echo "Calling API $ceTaskUrl  (Attempt $i)"
    curl -s -X GET $ceTaskUrl > result.json
	result=`cat result.json | $JQ '.task.status'`

	#cat result.json | underscore select '.task .status'
	#result = jq -r '.task.status'
    #result=`cat result.json | jq -r '.task.status'`
    echo "Result = $result"
	
	if [ $result = "SUCCESS" ]; then
	  exit 0
      cat result.json
      cp result.json sonar-results
      exit 0
    fi

    if [ $result = "FAILED" ]; then
      echo "Failed to obtain Sonar result" > sonar-failure/results
      cat result.json >> sonar-failure/results
      fatal_file sonar-failure/results
    fi

    if [ $result = "CANCELED" ]; then
      echo "Sonar analysis was CANCELED" > sonar-failure/results
      fatal_file sonar-failure/results
    fi

    echo "Sleeping 1 second until next try..."
    sleep 1
	


    i=$(( i + 1 ))
	
done

echo "Reading sonar results..."

analysisId=`$JQ -r '.task.analysisId' <result.json`

echo "Reading analysis Id = $analysisId"

#Use the analysisId from the JSON returned by /api/ce/task?id=XXX
#Immediately call /api/qualitygates/project_status?analysisId=YYY to check the status of the quality gate
curl -s -X GET $SONAR_URL/api/qualitygates/project_status?analysisId=$analysisId >sonar.json

echo "Results of quality gate"
cat sonar.json
echo ""

overallRating=$( $JQ -r '.projectStatus.status' < sonar.json )

if [ $overallRating = "null" ]; then
  echo "Failed to obtain results" > sonar-failure/results

  fatal_file sonar-failure/results
fi

if [ $overallRating = "ERROR" ]; then
  securityRating=$( jq -r '.projectStatus.conditions[] | select(.metricKey=="new_security_rating") | .status' <sonar.json )
  reliabilityRating=$( jq -r '.projectStatus.conditions[] | select(.metricKey=="new_reliability_rating") | .status' <sonar.json )
  maintRating=$( jq -r '.projectStatus.conditions[] | select(.metricKey=="new_maintainability_rating") | .status' <sonar.json )

  echo "Failed sonar gate => Security = $securityRating  Reliability = $reliabilityRating  Maintainability = $maintRating" > sonar-failure/results
  echo "$dashboardUrl" >> sonar-failure/results

  fatal_file sonar-failure/results
fi

echo "Passed sonar gate"

