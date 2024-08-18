
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## sensor-ingest-service
Este microservicio es util para ingerir datos masivos provenientes de sensores IoT.

#### Dependencias
- **Java, version >=** [17.0.0](https://aws.amazon.com/es/corretto/)
- **Docker Engine, version >=** [24.0.2](https://www.docker.com/)
- **Gradle, version >=** [8.6](https://gradle.org/)
- **Spring boot: version >=** [3.3.2](https://spring.io/projects/spring-boot/)
- **Servidor RabbitMQ, version >=** [3.13.6](https://www.rabbitmq.com/)

#### Construye el servicio

  **Clona el proyecto, configura y construye el proyecto con Gradle:**
  ``` sh
  git clone git@192.168.1.174:insighttechio/sensor-ingest-service.git
  cd sensor-ingest-service/

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
     -t localhost:5000/sensor-ingest-service:1.0.0 \
     -t localhost:5000/sensor-ingest-service:latest .
   ```

   - Subir la imagen a un Docker Registry: **(Opcional)**
   ```sh
    docker push localhost:5000/sensor-ingest-service:1.0.0
    docker push localhost:5000/sensor-ingest-service:latest
   ```

   - Levantar el servicio en un contenedor docker:
   ```sh
   docker run --rm --name sensor-ingest-service-container \
     -p 8000:8000 localhost:5000/sensor-ingest-service:1.0.0
   ```
   Puedes utilizar los scripts incluidos en el proyecto para construir la imagen y levantar el servicio:
   ```sh
    #Construye la imagen desde Dockerfile
    #Persiste la imagen en un Docker Registry
    bash scripts/build-push-image.sh

    #Levanta el servicio en un Docker Container
    bash scripts/run-container-service.sh
   ```
##### Utilizando Gradle
Levanta el servidor embebido **Tomcat** de **Spring Boot**, con  gradle:
```sh
./gradlew bootRun
```

#### Consumo del servicio
Una vez levantado el servicio, este escuchará peticiones por el puerto y la url: http://localhost:8080/api/v1/ingestdata
```bash
#Usando curl: manda datos de ingesta al servicio:
curl -X POST -H 'Content-Type: application/json' \
-d '{
        "produced_by": "bash_script", 
        "sensorId": "sensor_temp1",
        "timestamp": "2024-08-02T16:24:13+00:00", 
        "temperature": "15","degree": "celsius"
}' \
localhost:8000/api/v1/ingestdata
```

