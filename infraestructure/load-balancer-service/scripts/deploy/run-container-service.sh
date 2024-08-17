#!/bin/bash
# Levantar el servicio en un contenedor docker
# NOTA: Este tipo de despliegue solo se debe de realizar en entornos de prueba o de desarrollo: las variables de entorno pueden tener datos sensibles.

# Perfilamiento 
# Los perfiles de "desarrollo" y "Produccion" apuntaran a una base de datos ORACLE

# Entorno pruebas o desarrollo: usar "default" levantara una instancia "H2 Database".
# Entorno desarrollo: usar "dev" apuntando a localhost o al servidor de desarrollo.
# Entorno producion: usar "prod" apuntando al servidor de produccion.

WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs) \

# Levanta un contenedor: sobreescribe la URI de PostgreSQL y RabbitMQ, variables de entorno.
function run-container() {
    docker run --restart=always -d \
     --network "$DOCKER_NETWORK" \
     -p 80:80 localhost:5000/"${IMAGE_NAME}:1.0.0"
}

function dev-run-container() {
    docker run --rm \
     --name load-balancer-service \
     --network portafolio_virtual_network \
     --ip 173.18.0.4 \
     -p 80:80 \
     localhost:5000/"${IMAGE_NAME}:1.0.0"
}


echo "Levantando el contenedor: $IMAGE_NAME"
echo

# run-container
dev-run-container

