#!/bin/bash
#Levantar el microservicio en un contenedor docker

WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

function run-container() {
    docker run --rm \
      --name sensor-ingest-service \
      --network portafolio_virtual_network \
      --ip 173.18.0.7 \
      -p 8000:8080 \
      -e SERVER_PORT=8080 \
      -e SPRING_RABBITMQ_HOST=rabbit_service \
      -e SPRING_RABBITMQ_PORT=5672 \
      -e SPRING_RABBITMQ_USERNAME=guest \
      -e SPRING_RABBITMQ_PASSWORD=guest \
      localhost:5000/sensor-ingest-service:1.0.0
}


echo "Levantando el contenedor: $IMAGE_NAME"
echo

run-container

