#!/bin/bash
#Script para listar sensores del service

sensorId=$1

if [ $sensorId ]; then 
    # Consulta un sensor por medio de un identificador unico - sensorId:
    curl -I -X DELETE localhost:8002/api/v1/sensors/${sensorId}
fi
