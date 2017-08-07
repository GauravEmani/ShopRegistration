#!/bin/sh

set -e # fail fast
set -x # print commands

git clone source-code new-repo

# cd new repo
# echo $(date) > bumpme

# git config --global user.email "nobody@concourse.ci"
# git config --global user.name "Concourse"

# git add .
# git commit -m "Bumped date"