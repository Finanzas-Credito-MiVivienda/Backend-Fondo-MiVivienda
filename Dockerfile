# Etapa 1: Build de la app con Maven y Java 17
FROM maven:3.8.8-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final para producción, solo con el JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/BackendFinanzas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
