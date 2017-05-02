# ShopRegistration
Registering shops using spring-boot microservice

Project Details:
Project Type: Maven project
Pre-requisites: apache-maven-3.3.3 or higher , Java8, POSTMAN

Steps to build the project :
1.	Download the zip or clone the repository
2.	Run > “mvn clean install”  using a cmd prompt in the root folder of the project

Run the Project from the cmd prompt
1.	mvn spring-boot:run

Use POSTMAN or HTTP Tool for the following steps
Server-Port : 64002

Dummy data provided in the request-body of POST method, and also in the url of GET Method
Adding shops :
Use the following url to post the shop data:
url : localhost:64002/shops
method type : POST
Request Body : { “shopName”: “test”, “postcode”: “411061”, “shopNumber”: “1”}

Fetching shops:
Use the following url with latitude and longitude info as path params:
Method type: GET
url: localhost:64002/location/shops?lattitude=”18.5882884”&longitude=”73.8169099”
