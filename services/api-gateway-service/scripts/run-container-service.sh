#!/bin/bash
#Levantar el microservicio en un contenedor docker

WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

function run-container() {
    docker run --rm \
      --name api-gateway-service1 \
      --network portafolio_virtual_network \
      --ip 173.18.0.12 \
      -p 8080:8080 \
      -e SPRING_PROFILES_ACTIVE=prod \
      localhost:5000/"${IMAGE_NAME}":1.0.0
}


echo "Levantando el contenedor: $IMAGE_NAME"
echo

run-container

