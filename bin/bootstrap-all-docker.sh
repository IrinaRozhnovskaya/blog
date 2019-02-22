#!/usr/bin/env bash

##############################################################################################################
## This script intended to use in Linux Alpine "Play with Docker" environment with "wget" already installed ##
##############################################################################################################

## Play with Docker
# source <(curl -s https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-docker.sh)
# source <(curl -s https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all-docker.sh)
# rm -rf bootstrap-all-docker.sh ; wget https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all-docker.sh && bash bootstrap-all-docker.sh daggerok/blog-1

## NOTE:
# for some reasons not worked: wget -qO- https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-docker.sh | bash

export BASE_DIR="/root"
cd ${BASE_DIR}

ARG1=$1
if [[ ".${ARG1}" == "." ]] ; then
  export GITHUB_REPO_ID="IrinaRozhnovskaya/blog"
else
  export GITHUB_REPO_ID="${ARG1}"
fi

apk add -q --upgrade git
git config --global user.name "Play with Docker"
git config --global user.email "play.with.docker@example.com"

export REPO_NAME="blog"
export REPO_DIR="${BASE_DIR}/${REPO_NAME}"
export GITHUB_REPO_URL="https://github.com/${GITHUB_REPO_ID}"

if [[ -d "${REPO_DIR}" ]] ; then
  echo "repo is already cloned."
  cd ${REPO_DIR}
  git fetch -p --all -q # RTFM git -C ...
  git pull -q origin master --force
else
  echo "clone a repo..."
  git clone -q ${GITHUB_REPO_URL} ${REPO_NAME}
  cd ${REPO_DIR}
fi

docker volume create --name build-data
docker run --rm --name build-blog -it -v build-data:/root/.m2  -v $(pwd):/tmp/blog              -w /tmp/blog     maven:3.6.0-jdk-8-alpine mvn -DskipTests -f docker/pom.xml -P copy
docker run --rm --name build-blog -it -v build-data:/root/.m2  -v $(pwd):/tmp/blog              -w /tmp/blog     maven:3.6.0-jdk-8-alpine mvn -DskipTests -f backend/pom.xml
docker run --rm --name build-blog -it -v build-data:/root/.m2  -v $(pwd):/tmp/blog              -w /tmp/blog     maven:3.6.0-jdk-8-alpine mvn -DskipTests -f blog-cli/pom.xml
docker run --rm --name build-blog -it -v build-data:/root/.npm -v $(pwd)/frontend:/tmp/frontend -w /tmp/frontend node:11.10.0-alpine      ash -c "npm i && npm run build-play-with-docker"

docker-compose -f docker/src/main/docker/docker-compose-all.yaml up -d --build
docker-compose -f docker/src/main/docker/docker-compose-all.yaml logs -f -t

# open http://${play-with-docker-generated-host}-80.direct.labs.play-with-docker.com/
