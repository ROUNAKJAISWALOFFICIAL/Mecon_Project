# Use Eclipse Temurin Java 17 (or 21 if your project uses it)
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file to the container
COPY target/*.jar app.jar

# Expose the port your app runs on (default 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
