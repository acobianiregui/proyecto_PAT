# 1. Etapa de construcción (build)
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias (para cachear)
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Construir el JAR, sin tests para acelerar (quita -DskipTests si quieres ejecutar tests)
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (runtime)
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiar el JAR generado desde la etapa build
COPY --from=build /app/target/p5-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto 8080 (puerto por defecto de Spring Boot)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
