FROM nginx:1.15.8-alpine
LABEL MAINTAINER='Maksim Kostromin https://github.com/daggerok'
HEALTHCHECK CMD true
ADD ./docker/src/main/docker/frontend/default.conf /etc/nginx/conf.d/default.conf
ADD ./frontend/dist /usr/share/nginx/html
