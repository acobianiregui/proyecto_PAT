#Hacer build
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

#Copiar html
COPY pom.xml .

RUN mvn dependency:go-offline -B

#Codigo fuente
COPY src ./src

#Construir el jar (me salto los tests que ya se han hecho en ci)
RUN mvn clean package -DskipTests

#Ejecucion
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

#Jar generado
COPY --from=build /app/target/p5-0.0.1-SNAPSHOT.jar app.jar

#Hay que exponer el puerto que se quiere usar (recordarlo en render)
EXPOSE 8080

#Ahora ejecutar app
ENTRYPOINT ["java", "-jar", "app.jar"]
