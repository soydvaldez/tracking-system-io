#!/bin/bash
#Script para dar de alta topics en rabbitmq

# docker cp ./rabbitmq/init.sh tracking-rabbitmq-container:/home/rabbitmq/init.sh
# docker exec tracking-rabbitmq-container chmod u+x /home/rabbitmq/init.sh

CONTAINER_NAME=rabbit_server

# Corre el script
docker exec "${CONTAINER_NAME}" /var/lib/rabbitmq/init.sh
