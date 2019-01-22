@echo off
mvnw -f backend\pom.xml -Pdocker -pl :backend docker-compose:down
