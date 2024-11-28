# Use an official Maven image to build the app
FROM maven:3.8.6-openjdk-11 AS build
#FROM maven:3.8.6-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY scanner_bot/ .

# Package the application
RUN mvn clean package

# Build the project
#RUN mvn clean install

# Debug: list the contents of the /app/target directory
RUN ls -l /app/target

# Use an official OpenJDK runtime as a parent image
FROM openjdk:11
#FROM openjdk:21-jre-slim

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/forta_linux_java_scanner_bot-1.0-SNAPSHOT.jar /app/scanner_bot.jar

# Run the Java application
ENTRYPOINT ["java -cp src bot.telegram.Main", "-jar", "/app/scanner_bot.jar"]