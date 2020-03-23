#!/bin/sh
set -e
./gradlew bootJar
cp ./build/libs/vladimir-bot-executable.jar docker/jar/app.jar
docker-compose -f ./docker/docker-compose.yml build