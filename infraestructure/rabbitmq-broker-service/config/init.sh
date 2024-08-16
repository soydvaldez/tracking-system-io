#!/bin/bash

# Esperar a que RabbitMQ esté listo
rabbitmqctl await_startup

# Crear un exchange
rabbitmqadmin declare exchange name=my_topic_exchange type=topic

# Crear una queue
rabbitmqadmin declare queue name=temperature-data durable=true
rabbitmqadmin declare queue name=NewSensorAdded durable=true
