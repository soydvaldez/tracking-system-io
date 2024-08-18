#!/bin/bash
#Construye una imagen docker y la agrega a un registry para almacenar la imagen generada.

WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

TAGS=("1.0.0" "latest")

function build_images() {
   docker build \
    -t localhost:5000/"${IMAGE_NAME}":${TAGS[0]} \
    -t localhost:5000/"${IMAGE_NAME}":${TAGS[1]} .
}

function push_images() {
    for TAG in "${TAGS[@]}"; do
        docker push localhost:5000/"${IMAGE_NAME}":"${TAG}"
    done
}

function list_images() {
    echo "localhost:5000/"${IMAGE_NAME}":${TAGS[0]}"
    echo "localhost:5000/"${IMAGE_NAME}":${TAGS[1]}"
}

./gradlew clean build && build_images && push_images && list_images