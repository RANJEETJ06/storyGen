# Use official OpenJDK image from Eclipse Temurin
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file from the target directory
COPY target/storyGen-0.0.1-SNAPSHOT.jar app.jar

# Expose your Spring Boot server port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
