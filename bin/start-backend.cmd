@echo off
call mvnw.cmd -Pdocker docker-compose:up -f .\backend\pom.xml
call docker-compose -f .\backend\docker-compose.yaml logs -f -t
