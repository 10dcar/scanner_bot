# Use an official Maven image to build the project
FROM maven:3.8.4-jdk-11 AS build

# Set the working directory
WORKDIR /bots/scanner_bot

# Copy the project files to the working directory
COPY . .

# Build the Maven project
RUN mvn clean install

# Use an official OpenJDK image to run the application
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /bots/scanner_bot

# Copy the jar file from the build stage
COPY --from=build /app/target/scanner_bot.jar /app/scanner_bot.jar

# Run the Java application
ENTRYPOINT ["java", "-jar", "scanner_bot.jar"]