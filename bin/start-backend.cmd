@echo off
call mvnw.cmd -f .\docker\pom.xml -P up
call .\bin\tail-logs.cmd
