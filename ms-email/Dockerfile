# Stage 1: Build Maven project
FROM openjdk:17-jre-slim

# Set working directory inside the container
WORKDIR /to-build-app

# Copy the POM file to the container
COPY pom.xml .

# Resolve Maven dependencies (downloads dependencies into Docker cache)
RUN mvn dependency:go-offline

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Create final Docker image
FROM adoptopenjdk:17-jre-hotspot AS runtime-image

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build-image /to-build-app/target/*.jar app.jar

# Expose the port that the application listens on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
