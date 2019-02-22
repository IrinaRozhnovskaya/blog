#!/usr/bin/env bash

##############################################################################
## This script intended to use in CoreOS Linux "Play with K8s" environment  ##
##############################################################################

## Play with Kubernetes: https://labs.play-with-k8s.com/
# source <(curl -s https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-k8s-re.sh)
# source <(curl -s https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all-k8s-re.sh)

## NOTE:
# for some reasons not worked: wget -qO- https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-re.sh | bash

cd /root/blog

git fetch -p --all -q # RTFM git -C ...
git pull -q origin master --force

./mvnw -f ./pom.xml
./mvnw -f ./frontend/pom.xml -P build-play-with-docker
./mvnw -f ./docker/pom.xml -P all-up
docker-compose -f ./docker/src/main/docker/docker-compose-all.yaml logs -f -t

# open http://${play-with-k8s-generated-host}-80.direct.labs.play-with-k8s.com/
