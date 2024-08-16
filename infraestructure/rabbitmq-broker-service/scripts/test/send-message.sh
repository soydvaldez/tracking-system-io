#!/bin/bash
# Script para enviar mensajes a una cola al servidor rabbitMQ

curl -i -u guest:guest -H "Content-Type: application/json" \
  -X POST \
  -d '{
    "properties": {},
    "routing_key": "my-routing-key",
    "payload": "{\"produced_by\": \"bash_script\", \"sensorId\": \"sensor_temp1\", \"timestamp\": \"2024-08-14T21:20:08+00:00\", \"temperature\": \"53\", \"degree\": \"celsius\"}",
    "payload_encoding": "string"
  }' \
  http://localhost:15672/api/exchanges/%2f/my-exchange/publish