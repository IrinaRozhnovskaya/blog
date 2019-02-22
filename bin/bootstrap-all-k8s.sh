#!/usr/bin/env bash

##############################################################################
## This script intended to use in CoreOS Linux "Play with K8s" environment  ##
##############################################################################

## Play with Kubernetes: https://labs.play-with-k8s.com/
# source <(curl -s https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-k8s.sh)
# source <(curl -s https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all-k8s.sh)
# yum install -y wget bash ; rm -rf bootstrap-all-k8s.sh ; wget https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all-k8s.sh && bash bootstrap-all-k8s.sh daggerok/blog-1

## NOTE:
# for some reasons not worked: wget -qO- https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all.sh | bash

export BASE_DIR="/root"
cd ${BASE_DIR}

ARG1=$1
if [[ ".${ARG1}" == "." ]] ; then
  export GITHUB_REPO_ID="IrinaRozhnovskaya/blog"
else
  export GITHUB_REPO_ID=${ARG1}
fi
export GITHUB_REPO_URL="https://github.com/${GITHUB_REPO_ID}"

yum install -y -q java-1.8.0-openjdk-devel maven bash git
export JAVA_HOME="/usr/lib/jvm/java-openjdk"
export PATH="${PATH}:${JAVA_HOME}/bin"

git config --global user.name "Play with K8s"
git config --global user.email "play.with.k8s@example.com"

export REPO_NAME="blog"
export REPO_DIR="${BASE_DIR}/${REPO_NAME}"

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

./mvnw -f ./pom.xml
./mvnw -f ./frontend/pom.xml -P build-play-with-docker
./mvnw -f ./docker/pom.xml -P all-up
docker-compose -f ./docker/src/main/docker/docker-compose-all.yaml logs -f -t

# open http://${play-with-k8s-generated-host}-80.direct.labs.play-with-k8s.com/
