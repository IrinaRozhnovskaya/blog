@echo off
mvnw -f .\docker\pom.xml -Pdocker -pl :docker docker-compose:down
