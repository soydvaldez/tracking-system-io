#!/bin/bash
# Esperar a que RabbitMQ esté listo
rabbitmq-server &
pid="$!"

# Esperar que RabbitMQ esté completamente operativo
until rabbitmqctl status; do
  echo "Esperando a que RabbitMQ esté listo..."
  sleep 5
done

# Importar definiciones
rabbitmqctl import_definitions /etc/rabbitmq/definitions.json

# Mantener el proceso de RabbitMQ activo
wait "$pid"