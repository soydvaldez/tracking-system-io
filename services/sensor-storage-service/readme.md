
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)


## sensor-storage-service
Este microservicio es util para persistir datos masivos provenientes de mensajes originados por un bus de eventos gestionado por RabbitMQ, para posteriomente persistirlos en un repositorio MongoDB.

Una vez levantado el servicio, este establecera comunicacion con bus de eventos y se subscribirá a una cola denominada: "temperature-data" para recibír mensajes provenientes desde un **servidor RabbitMQ**. Posteriormente, el mensaje será procesado por reglas de negocio especificas y finalmente será persistido en un **servidor base de datos NoSQL Mongo**.

#### Dependencias
- **Java, version >=** [17.0.0](https://aws.amazon.com/es/corretto/)
- **Docker Engine, version >=** [24.0.2](https://www.docker.com/)
- **Gradle, version >=** [8.6](https://gradle.org/)
- **Spring boot: version >=** [3.3.2](https://spring.io/projects/spring-boot/)
- **Servidor RabbitMQ, version >=** [3.13.6](https://www.rabbitmq.com/)
- **Servidor MongoDB, version >=** [8.0.0](https://hub.docker.com/_/mongo)
- **JUnit, version >=** [5.11](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api)

#### Construye el servicio

  **Clona el proyecto, configura y construye el proyecto con Gradle:**
  ``` sh
  git clone git@192.168.1.174:insighttechio/sensor-storage-service.git
  cd sensor-storage-service/

  # Descarga la dependencia de gradle, definida en: gradle.properties
  gradle wrapper

  # Limpia los ejecutables del proyecto. Descarga las dependencias para crear un ejecutable: .JAR
  # Se salta las pruebas: -x test
  ./gradlew clean build -x test
  ```
#### Levanta el servicio
##### Utilizando Docker
   - Construye la imagen personalizada con docker build:
   ```sh
    docker build \
     -t localhost:5000/sensor-storage-service:1.0.0 \
     -t localhost:5000/sensor-storage-service:latest .
   ```

   - Subir la imagen a un Docker Registry: **(Opcional)**
   ```sh
    docker push localhost:5000/sensor-storage-service:1.0.0
    docker push localhost:5000/sensor-storage-service:latest
   ```

   - Levantar el servicio en un contenedor docker:
   ```sh
   docker run --rm \
    --network iotracksystem_test_network \
    --name sensor-storage-service-container \
    localhost:5000/sensor-storage-service:1.0.0
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

#### Correr testing del servicio
Para realizar el testing de todas las pruebas escritas para el servicio, se pueden realizar mediante correr el comando:
```bash
#Ejecutará todas las pruebas definidas para el servicio
./gradlew test
```
**Pruebas de Integracion con RabbitMQ**

Durante el levantamiento del contexto y en la ejecucción de las pruebas, se construye y se despliega un **contenedor docker con una imagen de un servidor RabbitMQ**. En la prueba se crean algunos mensajes, los cuales son enviados al contenedor. Posteriormente el servicio recibe los mensajes enviados desde el servidor RabbitMQ para validar la integridad con JUnit de cada uno.

```bash
#Para realizar el testing para el envio y recepcion de mensajes desde un servidor RabbitMQ, ejecutar:
./gradlew test --tests "RabbitMQTest.testSendAndReceive"
```

