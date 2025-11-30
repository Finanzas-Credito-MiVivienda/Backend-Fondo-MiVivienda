# Etapa 1: Build con Maven + OpenJDK 22
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Java 22
FROM eclipse-temurin:22-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/BackendFinanzas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]