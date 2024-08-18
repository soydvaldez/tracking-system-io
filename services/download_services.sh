#!/bin/bash

# Define los repositorios de los servicios
repositorios=(
    "git@192.168.1.174:insighttechio/api-gateway-service.git"
    "git@192.168.1.174:insighttechio/sensor-ingest-service.git"
    "git@192.168.1.174:insighttechio/sensor-storage-service.git"
    "git@192.168.1.174:insighttechio/sensor-manager-service.git"
)
    

# Define las rutas donde se clonarán los servicios
carpetas=(
    "api-gateway-service"
    "sensor-ingest-service"
    "sensor-storage-service"
    "sensor-manager-service"
)

# Iterar sobre los repositorios y clonar o actualizar
for i in "${!repositorios[@]}"; do
    if [ -d "${carpetas[$i]}" ]; then
        echo "Actualizando ${carpetas[$i]}..."
        cd "${carpetas[$i]}"
        git pull origin main
        cd ..
    else
        echo "Clonando ${carpetas[$i]}..."
        git clone "${repositorios[$i]}" "${carpetas[$i]}"
    fi
done

echo "Todos los servicios han sido clonados o actualizados."
