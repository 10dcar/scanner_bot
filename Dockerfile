# Use an official Maven image to build the app
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY scanner_bot/ .

# Package the application and copy dependencies
RUN mvn clean package
RUN mvn dependency:copy-dependencies

# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

# Set the working directory in the runtime container
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/forta_linux_java_scanner_bot-1.0-SNAPSHOT.jar /app/scanner_bot.jar

# Copy the dependencies
#COPY --from=build /app/target/dependency /app/dependency
#COPY --from=build /app/target/classes /app/dependency
#COPY --from=build /app/src/main/resources /app/dependency

COPY --from=build /app/target/dependency /app/dependency
#COPY --from=build /app/target/classes /app/dependency
COPY --from=build /app/target/classes/config.json /app/src/main/resources/config.json
#COPY --from=build /app/src/main/resources /app/dependency

# Set the classpath and run the Java application
CMD ["sh", "-c", "export CLASSPATH=/app/scanner_bot.jar:/app/dependency/* && java bot.telegram.Main"]