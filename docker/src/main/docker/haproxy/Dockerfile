FROM haproxy:1.9.3-alpine
HEALTHCHECK CMD true
RUN apk add --update --no-cache curl
COPY ./docker/src/main/docker/haproxy/haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
