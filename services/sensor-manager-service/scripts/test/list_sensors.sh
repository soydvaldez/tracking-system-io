#!/bin/bash
#Script para listar sensores del service

# Lista los sensores registrados:
curl localhost:8002/api/v1/sensors | jq '.'