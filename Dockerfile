FROM maven:3.9.4-eclipse-temurin-20 AS jar

# Build in a separated location which won't have permissions issues.
WORKDIR /opt/wcf
# Any changes to the pom will affect the entire build, so it should be copied first.
COPY pom.xml ./pom.xml
# Grab all the dependencies listed in the pom early, since it prevents changes to source code from requiring a complete re-download.
# Skip compiling tests since we don't want all the dependecies to be downloaded.
RUN mvn -f ./pom.xml clean dependency:go-offline -Dmaven.test.skip -T 1C
# Source code changes may not change dependencies, so it can go last.
# Skip compiling tests since we don't want all the dependecies to be downloaded for plugins.
COPY src ./src
RUN mvn -f ./pom.xml clean package -Dmaven.test.skip -T 1C

#
# Server creation stage
#
FROM openjdk:22-jdk

# Host the server in a location that won't have permissions issues.
WORKDIR /opt/server
# Copy the JAR we build earlier.
COPY --from=jar /opt/wcf/target/WatchCaseFactory-0.0.1-SNAPSHOT.jar ./Server.jar
# Default exposure, although not required if using docker compose.
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./Server.jar"]