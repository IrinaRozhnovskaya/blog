#!/usr/bin/env bash

##############################################################################################################
## This script intended to use in Linux Alpine "Play with Docker" environment with "wget" already installed ##
##############################################################################################################e

## start from these commands:
# apk add --upgrade wget
# wget -qO- https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all.sh | bash
# time $(wget -qO- https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all.sh | bash)

export REPO_NAME="blog"
export GITHUB_REPO_ID="daggerok/blog-1"

export JDK8_VERSION="8u202"
export JDK8_PATCH_VERSION="b08"
export JDK_ARCHIVE="OpenJDK8U-jdk_x64_linux_hotspot_${JDK8_VERSION}${JDK8_PATCH_VERSION}.tar.gz"
export ADOPT_JDK8_URL="https://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk${JDK8_VERSION}-${JDK8_PATCH_VERSION}/${JDK_ARCHIVE}"
export BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

export JDK8_DIRNAME="jdk${JDK8_VERSION}-${JDK8_PATCH_VERSION}"
export JAVA_HOME="${BASE_DIR}/${JDK8_DIRNAME}"
export PATH="${PATH}:${JAVA_HOME}/bin"

export REPO_DIR="${BASE_DIR}/${REPO_NAME}"
export GITHUB_REPO_URL="https://github.com/${GITHUB_REPO_ID}"

#export ENVS="envs.sh"
#echo "export REPO_NAME=${REPO_NAME}" > /etc/profile.d/${ENVS}
#echo "export GITHUB_REPO_ID=${GITHUB_REPO_ID}" >> /etc/profile.d/${ENVS}
#echo "export JDK8_VERSION=${JDK8_VERSION}" >> /etc/profile.d/${ENVS}
#echo "export JDK8_PATCH_VERSION=${JDK8_PATCH_VERSION}" >> /etc/profile.d/${ENVS}
#echo "export JDK_ARCHIVE=${JDK_ARCHIVE}" >> /etc/profile.d/${ENVS}
#echo "export ADOPT_JDK8_URL=${ADOPT_JDK8_URL}" >> /etc/profile.d/${ENVS}
#echo "export BASE_DIR=${BASE_DIR}" >> /etc/profile.d/${ENVS}
#echo "export JDK8_DIRNAME=${JDK8_DIRNAME}" >> /etc/profile.d/${ENVS}
#echo "export GITHUB_REPO_URL=${GITHUB_REPO_URL}" >> /etc/profile.d/${ENVS}
#chmod +x /etc/profile.d/${ENVS}
#. /etc/profile.d/${ENVS}

if [[ ! -d "${BASE_DIR}/${JDK8_DIRNAME}" ]] ; then
  echo "install git..."
  apk add -q --upgrade git bash wget
  echo "install jdk8..."
  wget -qO- ${ADOPT_JDK8_URL} | tar -xz
fi

git config --global user.name "Play With Docker"
git config --global user.email "play.with.docker@example.com"

if [[ -d "${REPO_DIR}" ]] ; then
  echo "repo is already cloned!";
  git -C ${REPO_DIR} fetch -p --all -q # RTFM git -C ...
  git -C ${REPO_DIR} pull -q origin master --force
else
  echo "clone a repo..."
  git clone -q ${GITHUB_REPO_URL} ${REPO_NAME}
fi

echo "please run these commands:"
echo "export JAVA_HOME=${JAVA_HOME}"
echo "export PATH=${PATH}:${JAVA_HOME}/bin"
echo ". ${REPO_DIR}/mvnw -f ${REPO_DIR}/pom.xml"
echo ". ${REPO_DIR}/mvnw -f ${REPO_DIR}/docker/pom.xml -P all-up"
