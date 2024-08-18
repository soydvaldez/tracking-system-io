FROM openjdk:17-jdk-slim

WORKDIR /app
COPY ./build/libs/sensor-manager-service-0.0.1-SNAPSHOT.jar ./app.jar

# Define un perfil por defecto:
# ENV SPRING_PROFILES_ACTIVE=test

EXPOSE 8002
    
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
