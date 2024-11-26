# Use an official Maven image to build the app
FROM maven:3.8.7-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY HelloWorldDocker/ .

# Package the application
RUN mvn clean package

# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

# Set the working directory
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/forta_linux_java_scanner_bot-1.0-SNAPSHOT.jar /app/scanner_bot.jar

# Run the Java application
ENTRYPOINT ["java", "-jar", "/app/scanner_bot.jar"]