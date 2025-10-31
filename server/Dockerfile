# ---- Stage 1: Build with Maven ----
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Package application (skip tests to speed up build)
RUN mvn clean package -DskipTests

# ---- Stage 2: Runtime image ----
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
