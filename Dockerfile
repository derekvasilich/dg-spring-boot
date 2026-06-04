# Multi-stage Dockerfile for building and running dg-spring-boot

FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /workspace/app

COPY . ./
RUN chmod +x ./mvnw
RUN env -u MAVEN_CONFIG ./mvnw -B -DskipTests package

FROM eclipse-temurin:11-jre-jammy
WORKDIR /app
COPY --from=build /workspace/app/target/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
