#!/usr/bin/env bash
./bin/stop-backend.sh
./mvnw -pl :backend clean package
./mvnw -pl :docker -Pup
./bin/tail-logs.sh
