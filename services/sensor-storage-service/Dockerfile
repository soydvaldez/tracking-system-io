# FROM gradle:8.6.0-jdk17-alpine AS build

# WORKDIR /home/gradle/project

# COPY ./gradlew ./gradlew
# COPY ./gradle ./gradle
# COPY ./build.gradle ./build.gradle
# COPY ./settings.gradle ./settings.gradle
# COPY ./src ./src

# RUN gradle wrapper
# RUN chmod u+x ./gradlew
# RUN ./gradlew clean build
FROM amazoncorretto:17-alpine3.16

# MongoDB Server connection properties
# ENV SPRING_DATA_MONGODB_URI=mongodb://admin:admin@173.18.0.3:27017/admin

# RabbitMQ Server connection properties
# ENV SPRING_PROFILES_ACTIVE=dev

# ENV SPRING_RABBITMQ_HOST=rabbit_server
# ENV SPRING_RABBITMQ_PORT=5672
# ENV SPRING_RABBITMQ_USERNAME=guest
# ENV SPRING_RABBITMQ_PASSWORD=guest

WORKDIR /app
COPY ./build/libs/sensor-storage-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]