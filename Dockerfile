# Stage 1: Build the Spring Boot JAR using Maven
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY server/pom.xml .
COPY server/src ./src
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the Spring Boot app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
