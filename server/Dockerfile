# ===== Build stage =====
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the Spring Boot JAR (skip tests to save time)
RUN mvn clean package -DskipTests

# ===== Run stage =====
FROM eclipse-temurin:22-jdk
WORKDIR /app

# Copy built JAR from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose backend port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
