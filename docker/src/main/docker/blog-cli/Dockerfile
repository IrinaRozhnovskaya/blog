FROM openjdk:8u191-jre-alpine3.9
LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'
HEALTHCHECK CMD true
ADD ./blog-cli/target/*-all.jar /tmp/app.jar
ENTRYPOINT java -jar /tmp/app.jar
