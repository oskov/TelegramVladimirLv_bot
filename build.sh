#!/bin/sh
set -e
echo "Create jar"
./gradlew bootJar
echo "Move jar"
cp ./build/libs/vladimir-bot-executable.jar docker/jar/app.jar
echo "Docker-compose build"
docker-compose -f ./docker/docker-compose.yml build