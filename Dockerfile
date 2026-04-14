FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

RUN mvn -pl notification-service -am clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY notification-service/target/notification-service-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
