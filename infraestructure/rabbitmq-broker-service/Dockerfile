# Usa la imagen oficial de RabbitMQ como base
FROM rabbitmq:3-management

WORKDIR /var/lib/rabbitmq

COPY ./config/definitions.json /etc/rabbitmq/definitions.json
COPY ./config/entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod u+x /usr/local/bin/entrypoint.sh

# Copia el script de inicialización en el contenedor
# COPY ./config/init.sh ./init.sh
# RUN chmod u+x ./init.sh

# RUN apt-get update && apt-get install -y vim

# Exponer los puertos necesarios
EXPOSE 5672 15672

# Configurar el entrypoint
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

# Ejecutar el script de inicialización después de que RabbitMQ se haya iniciado
CMD ["rabbitmq-server"]