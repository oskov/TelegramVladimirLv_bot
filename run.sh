#!/bin/sh
echo "Docker-compose run"
docker-compose -f ./docker/docker-compose.yml up --abort-on-container-exit