FROM amazoncorretto:17-alpine3.16

WORKDIR /app

# Set profile of spring environment
ENV SPRING_PROFILES_ACTIVE=prod
COPY ./build/libs/gatewayservice-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]