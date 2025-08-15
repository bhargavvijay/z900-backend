# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Cache deps first
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -q -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Render sets PORT; keep default 8080 for local runs
ENV PORT=8080
EXPOSE 8080

# Copy the built jar to a fixed name
COPY --from=build /app/target/*.jar /app/app.jar

# Use PORT env for Render
CMD ["sh", "-c", "java -Xms256m -Xmx512m -jar /app/app.jar --server.port=${PORT}"]
