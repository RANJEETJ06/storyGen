# Use Maven to build the project
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use slim JDK image to run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/storyGen-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
