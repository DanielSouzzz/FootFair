FROM eclipse-temurin:17-jdk as builder

WORKDIR /app
COPY . /app

RUN ./mvnw package -DskipTests

# Roda o JAR
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
