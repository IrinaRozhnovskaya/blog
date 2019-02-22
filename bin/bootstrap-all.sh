#!/usr/bin/env bash

##############################################################################################################
## This script intended to use in Linux Alpine "Play with Docker" environment with "wget" already installed ##
##############################################################################################################

## Play with Docker
# source <(curl -s https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all.sh)

## Play with Kubernetes
# source <(curl -s https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all.sh)

## NOTE:
# for some reasons not worked: wget -qO- https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all.sh | bash

ARG1=$1
if [[ ".${ARG1}" == "." ]] ; then
  export GITHUB_REPO_ID="IrinaRozhnovskaya/blog"
else
  export GITHUB_REPO_ID="${ARG1}"
fi

export REPO_NAME="blog"
export BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
export REPO_DIR="${BASE_DIR}/${REPO_NAME}"
export GITHUB_REPO_URL="https://github.com/${GITHUB_REPO_ID}"

yum install -yq git bash
apk add -q --upgrade git bash
git config --global user.name "Play with Docker / Kubernetes User"
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

# open http://${play-with-docker-generated-host}-80.direct.labs.play-with-docker.com/
# open http://${play-with-k8s-generated-host}-80.direct.labs.play-with-k8s.com/
