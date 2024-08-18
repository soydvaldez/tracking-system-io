#!/bin/bash
# Genera wrapper de cada service y general los ejecutables JAR

# Obtiene la ruta absoluta del directorio donde se encuentra el script
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
echo "SCRIPT_DIR     $SCRIPT_DIR"

# Define los repositorios de los servicios
carpetas=(
    "api-gateway-service"
    "sensor-ingest-service"
    "sensor-manager-service"
    "sensor-storage-service"
)

function generate_wrapper() {
    cd $SCRIPT_DIR
    for carpeta in "${carpetas[@]}"; do
        if [[ -d "${carpeta}" && -f "${carpeta}/build.gradle" ]]; then
            echo "Generating Gradle Wrapper for ${carpeta}..."
            gradle wrapper -p "${carpeta}/"
        else
            echo "Directory ${carpeta} does not exist."
        fi
    done
}

function build_services() {
    cd $SCRIPT_DIR
    for carpeta in "${carpetas[@]}"; do
        if [ -d "${carpeta}" ]; then
            echo "Building ${carpeta}..."
            ./${carpeta}/gradlew clean build -x test -p "${carpeta}/"
        else
            echo "Directory ${carpeta} does not exist."
        fi
    done
}

generate_wrapper && build_services

