FROM mongo:latest

COPY ./config/mongod.conf /etc/mongo/mongod.conf
COPY ./config/createUser.js /docker-entrypoint-initdb.d/
# Aqui define el archivo de configuraciones
RUN apt-get update && apt-get install jq

EXPOSE 27017
# ENTRYPOINT ["mongod"]
