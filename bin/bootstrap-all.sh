#!/usr/bin/env bash

##############################################################################################################
## This script intended to use in Linux Alpine "Play with Docker" environment with "wget" already installed ##
##############################################################################################################

# wget -qO- https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all.sh | bash

export REPO_NAME="blog"
export GITHUB_REPO_ID="daggerok/blog-1"
export BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
export REPO_DIR="${BASE_DIR}/${REPO_NAME}"
export GITHUB_REPO_URL="https://github.com/${GITHUB_REPO_ID}"

apk add -q --upgrade git nodejs npm
git config --global user.name "Play With Docker"
git config --global user.email "play.with.docker@example.com"

if [[ -d "${REPO_DIR}" ]] ; then
  echo "repo is already cloned!"
  git -C ${REPO_DIR} fetch -p --all -q # RTFM git -C ...
  git -C ${REPO_DIR} pull -q origin master --force
else
  echo "clone a repo..."
  git clone -q ${GITHUB_REPO_URL} ${REPO_NAME}
fi

docker volume create --name build-data
docker run --rm --name build-blog -it -v build-data:/root/.m2   -v $(pwd)/blog:/tmp/blog -w /tmp/blog maven:3.6.0-jdk-8-alpine mvn -DskipTests -f backend/pom.xml
docker run --rm --name build-blog -it -v build-data:/root/.m2   -v $(pwd)/blog:/tmp/blog -w /tmp/blog maven:3.6.0-jdk-8-alpine mvn -DskipTests -f blog-cli/pom.xml
docker run --rm --name build-blog -it -v build-data:/root/.npm  -v $(pwd)/blog/frontend:/tmp/frontend -w /tmp/frontend node:11.10.0-alpine ash -c "npm i && npm run build"

docker-compose -f blog/docker/src/main/docker/docker-compose-all.yaml down -v --rmi local
docker-compose -f blog/docker/src/main/docker/docker-compose-all.yaml build --pull --force-rm
docker-compose -f blog/docker/src/main/docker/docker-compose-all.yaml up -d
docker-compose -f blog/docker/src/main/docker/docker-compose-all.yaml logs -f -t
