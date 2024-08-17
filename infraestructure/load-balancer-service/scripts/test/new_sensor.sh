#!/bin/bash
#Script para dar de alta un nuevo sensor
# Cargando Ambiente
WORKDIR=$(dirname $0)
# export $(grep -v "^#" $WORKDIR/.env | xargs)

source scripts/utils.sh

echo "=================================================="
echo "           $(echo parametros | toUpperCase)       "
echo "Tarea: Registra un Nuevo Sensor en la aplicacion  "
echo "WORKDIR: $WORKDIR/"
echo "Script: $WORKDIR/new_sensor.sh"
echo "Target: localhost:8002/api/v1/sensors"
echo "=================================================="

# Registra un nuevo sensor:
function add_new_sensor() {
    # Gegenerar numero aleatorio
    MIN=1; MAX=1000
    RANDOM_NUMBER=$((MIN + RANDOM % (MAX - MIN + 1)))
    
    sensorId="SENSOR00${RANDOM_NUMBER}"
    # De utilerias toma la fecha actual del sistema y lo asigna a la variable: "installationDate"
    installationDate=$(getCurrentDatetime)

    curl -s -X POST -H 'Content-Type: application/json' \
    -d '{
        "sensorId": "'"${sensorId}"'",
        "sensorName": "DHT22",
        "installationDate": "'"${installationDate}"'",
        "location": "Room 1",
        "status": "Active"
    }' \
    localhost:8002/api/v1/sensors | jq '.'
}

echo
add_new_sensor

# Por defecto estara activo el sensor
# El online sera el que se tendra que determinar con reglas de negocio