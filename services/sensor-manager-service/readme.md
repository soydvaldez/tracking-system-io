
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## sensor-manager-service
Este microservicio es util para gestionar sensores de temperatura IoT: consultar, consultar por id, dar de alta, actualizar y eliminar sensor.

#### Dependencias
- **Java, version >=** [17.0.0](https://aws.amazon.com/es/corretto/)
- **Docker Engine, version >=** [24.0.2](https://www.docker.com/)
- **Gradle, version >=** [8.6](https://gradle.org/)
- **Spring boot: version >=** [3.3.2](https://spring.io/projects/spring-boot/)
- **Servidor RabbitMQ, version >=** [3.13.6](https://www.rabbitmq.com/)

#### Construye el servicio

  **Clona el proyecto, configura y construye el proyecto con Gradle:**
  ``` sh
  git clone git@192.168.1.174:insighttechio/sensor-manager-service.git
  cd sensor-manager-service/

  # Descarga la dependencia de gradle, definida en: gradle.properties
  gradle wrapper

  # Limpia los ejecutables del proyecto. Descarga las dependencias para crear un ejecutable: .JAR
  ./gradlew clean build
  ```
#### Levanta el servicio
##### Utilizando Docker
   - Construye la imagen personalizada con docker build:
   ```sh
    docker build \
     -t localhost:5000/sensor-manager-service:1.0.0 \
     -t localhost:5000/sensor-manager-service:latest .
   ```

   - Subir la imagen a un Docker Registry: **(Opcional)**
   ```sh
    docker push localhost:5000/sensor-manager-service:1.0.0
    docker push localhost:5000/sensor-manager-service:latest
   ```

   - Levantar el servicio en un contenedor docker:
   ```sh
   docker run --rm --name sensor-manager-service-container \
     -p 8002:8002 localhost:5000/sensor-manager-service:1.0.0
   ```
   Puedes utilizar los scripts incluidos en el proyecto para construir la imagen y levantar el servicio:
   ```sh
    #Construye la imagen desde Dockerfile && persiste la imagen en un Docker Registry
    bash scripts/deploy/build-push-image.sh -y

    #Levanta el servicio en un Docker Container
    bash scripts/deploy/run-container-service.sh
   ```
##### Utilizando Gradle
Levanta el servidor embebido **Tomcat** de **Spring Boot**, con  gradle:
```sh
./gradlew bootRun
```

#### Consumo del servicio
Una vez levantado el servicio, este escuchará peticiones por el puerto y la url: http://localhost:8080/api/v1/sensors
Para registrar un nuevo sensor puedes correr el siguiente comando en la terminal

**Consultar los sensores dados de alta**
```bash
# Lista los sensores registrados:
curl localhost:8002/api/v1/sensors
```
**Dar de alta un nuevo sensor**
```bash
# Registra un nuevo sensor:
curl -X POST -H 'Content-Type: application/json' \
-d '{
      "sensorId": "SENSOR006",
      "sensorName": "DHT22",
      "installationDate": "2024-07-30T18:54:56Z",
      "location": "Room 1",
      "status": "Active"
  }' \
 localhost:8002/api/v1/sensors
```
**Consultar un sensor por id**
```bash
# Consultar un sensor a través de un identificador unico:
sensorId=SENSOR006
curl localhost:8002/api/v1/sensors/${sensorId}
```
**Actualizar sensor por id**
```bash
# Actualizar un sensor a través de un identificador unico:
sensorId=SENSOR006
curl -X PUT -H 'Content-Type: application/json' \
-d '{
    "sensorName": "DHT22_update",
    "installationDate": "2024-07-30T18:54:56Z",
    "location": "Room 1 update",
    "status": "Inactive"
  }' \
 localhost:8002/api/v1/sensors/${sensorId}
```
**Eliminar sensor por id**
```bash
# Eliminar un sensor a través de un identificador unico:
sensorId=SENSOR006
curl -X DELETE localhost:8002/api/v1/sensors/${sensorId}
```

