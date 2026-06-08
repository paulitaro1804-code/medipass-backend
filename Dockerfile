FROM openjdk:21

COPY "./target/medipass-backend-1.0.0.jar" "app.jar"
EXPOSE 8218
ENTRYPOINT ["java", "-jar", "app.jar"]