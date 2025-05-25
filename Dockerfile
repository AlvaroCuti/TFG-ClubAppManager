FROM maven:3.8.5-jdk-8 AS builder
WORKDIR /app
COPY target/TFG-ClubAppManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
