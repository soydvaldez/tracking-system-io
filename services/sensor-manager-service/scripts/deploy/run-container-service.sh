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

echo
echo "Datasource URL: \"$SPRING_DATASOURCE_URL\""
echo "Profile active: \"$PROFILES_ACTIVE\""
echo


function loadEnvironment() {
  if [[ $PROFILES_ACTIVE == "test" ]];then
    echo "cargando ambiente"
    # settea la relacion
    properties='{ "context_profile": "test", "url_database": "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"}'
  fi

  if [[ $PROFILES_ACTIVE == "dev" ]];then
    echo "cargando ambiente"
    # settea la relacion
    properties='{ "context_profile": "dev", "url_database": "jdbc:postgresql://173.18.0.2:5432/mydatabase"}'
  fi

  echo $properties | jq '.'
  echo
}


# Agrega un if validando el ambiente y el host:puerto

# Levanta un contenedor: sobreescribe la URI de PostgreSQL y RabbitMQ, variables de entorno.
function run-container() {
  context=$(echo $properties | jq -r '.context_profile')
  url=$(echo $properties | jq -r '.url_database')

  echo ${context}
  echo ${url}

  docker run --rm \
    --name "${IMAGE_NAME}" \
    --network portafolio_virtual_network \
    --ip 173.18.0.10 \
    -p 8002:8080 \
    -e SERVER_PORT=8080 \
    -e SPRING_RABBITMQ_HOST=rabbitmq-broker-service \
    -e SPRING_RABBITMQ_PORT=5672 \
    -e SPRING_RABBITMQ_USERNAME=guest \
    -e SPRING_RABBITMQ_PASSWORD=guest \
    -e SPRING_DATASOURCE_DRIVER-CLASS-NAME=org.postgresql.Driver \
    -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-service:5432/mydatabase \
    -e SPRING_DATASOURCE_USERNAME=postgres \
    -e SPRING_DATASOURCE_PASSWORD=postgres \
    localhost:5000/"${IMAGE_NAME}":1.0.0
}


echo "Levantando el contenedor: $IMAGE_NAME"
echo

# run-container
run-container

# Aqui sobrescribe y define variables para conectarte de forma interna a la red