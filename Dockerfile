# ===== Build stage =====
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY server/pom.xml .
RUN mvn dependency:go-offline
COPY server/src ./src
RUN mvn clean package -DskipTests

# ===== Run stage =====
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
