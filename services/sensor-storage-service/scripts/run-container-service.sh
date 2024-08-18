#!/bin/bash
#Levantar el microservicio en un contenedor docker

WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

function run-container() {
  echo
  echo "----------------------------------------------------------"
  echo "Levantado contenedor: $IMAGE_NAME"
  echo "----------------------------------------------------------"
  echo
  #  -e SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE \
  docker run --name sensor-storage-service --rm \
   --network portafolio_virtual_network \
   -e SPRING_RABBITMQ_HOST=$SPRING_RABBITMQ_HOST \
   -e SPRING_DATA_DATABASE=trackingsystem \
   -e SPRING_DATA_MONGODB_URI=mongodb://usertest:usertest@mongo-service:27017/trackingsystem \
   localhost:5000/sensor-storage-service
}


echo "Levantando el contenedor: localhost:5000/$IMAGE_NAME:1.0.0"
echo

run-container

