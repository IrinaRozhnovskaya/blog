@echo off
call mvnw.cmd clean -f .\backend\pom.xml
call mvnw.cmd -Pdocker docker-compose:down -f .\backend\pom.xml
call mvnw.cmd package -f .\backend\pom.xml
