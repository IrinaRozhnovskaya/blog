#!/usr/bin/env bash

################################################################################
## This script intended to use in Linux Alpine "Play with Docker" environment ##
################################################################################

## Play with Docker
# source <(curl -s https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-docker-re.sh)
# source <(curl -s https://raw.githubusercontent.com/daggerok/blog-1/master/bin/bootstrap-all-docker-re.sh)

## NOTE:
# for some reasons not worked: wget -qO- https://raw.githubusercontent.com/IrinaRozhnovskaya/blog/master/bin/bootstrap-all-docker-re.sh | bash

cd /root/blog
git fetch -p --all -q # RTFM git -C ...
git pull -q origin master --force

docker run --rm --name build-blog -it -v build-data:/root/.m2 -v $(pwd):/tmp/blog -w /tmp/blog maven:3.6.0-jdk-8-alpine mvn -DskipTests -f blog-cli/pom.xml
docker-compose -f docker/src/main/docker/docker-compose-all.yaml up   -d --build
docker-compose -f docker/src/main/docker/docker-compose-all.yaml logs -f -t

# open http://${play-with-docker-generated-host}-80.direct.labs.play-with-docker.com/
