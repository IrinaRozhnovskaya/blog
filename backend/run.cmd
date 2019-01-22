@echo off
@rem call mvnw.cmd
@rem call mvnw.cmd -Pdocker docker-compose:up
@rem call docker-compose logs -f -t
call mvnw.cmd clean
call mvnw.cmd -Pdocker docker-compose:down
call mvnw.cmd package
call mvnw.cmd -Pdocker docker-compose:up
call docker-compose logs -f -t
