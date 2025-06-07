# Etapa de compilación
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copiar todos los archivos del proyecto
COPY . .

# Compilar la aplicación (omitiendo pruebas para agilizar)
RUN chmod +x mvnw && ./mvnw clean package -DskipTests
# Etapa final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el artefacto JAR desde la etapa de compilación
COPY --from=builder /app/target/agify-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]