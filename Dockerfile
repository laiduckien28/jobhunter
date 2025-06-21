FROM eclipse-temurin:24-jre-alpine

WORKDIR /app

COPY build/libs/jobhunter-0.0.1-SNAPSHOT.jar /app/jobhunter.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/jobhunter.jar"]
