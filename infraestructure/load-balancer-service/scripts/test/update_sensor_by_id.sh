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
function update_sensor() {
    local sensorId=$1
    # De utilerias toma la fecha actual del sistema y lo asigna a la variable: "installationDate"
    installationDate=$(getCurrentDatetime)

    curl -s -X PUT -H 'Content-Type: application/json' \
    -d '{
        "sensorName": "DHT22_update",
        "installationDate": "'"${installationDate}"'",
        "location": "Room 1_update",
        "status": "Active_update"
    }' \
    localhost:8002/api/v1/sensors/${sensorId} | jq '.'
}

echo
sensorId=$1

if [ $sensorId ]; then
    update_sensor $sensorId
fi

# Por defecto estara activo el sensor
# El online sera el que se tendra que determinar con reglas de negocio