# ----- Stage 1: Build -----
FROM gradle:8.7-jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# ----- Stage 2: Run -----
FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/jobhunter-0.0.1-SNAPSHOT.jar /app/jobhunter.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/jobhunter.jar"]
