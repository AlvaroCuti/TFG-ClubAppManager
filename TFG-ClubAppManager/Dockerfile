FROM maven:3.8.5-openjdk-11 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:resolve

COPY src ./src
RUN mvn package spring-boot:repackage

FROM eclipse-temurin:11-jdk
WORKDIR /app

COPY --from=builder /app/target/TFG-ClubAppManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]