# Usamos una imagen de OpenJDK adecuada para Spring Boot
FROM eclipse-temurin:21-jdk-alpine

# Configuramos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo JAR del proyecto al contenedor
COPY target/agify-api-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación dentro del contenedor
CMD ["java", "-jar", "app.jar"]