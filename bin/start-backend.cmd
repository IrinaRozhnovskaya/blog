@echo off
call mvnw.cmd -Pdocker docker-compose:up -f .\docker\pom.xml
call docker-compose -f .\docker\docker-compose.yaml logs -f -t
