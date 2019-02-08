@echo off
call mvnw.cmd clean -f .\backend\pom.xml
call mvnw.cmd -f .\docker\pom.xml -P down
call mvnw.cmd package -f .\backend\pom.xml -DskipTests
