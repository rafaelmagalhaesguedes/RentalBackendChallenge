# Stage 1: Build Maven project
FROM eclipse-temurin:17-jdk-jammy as build-image

# Set working directory inside the container
WORKDIR /to-build-app

# Copy the Maven Wrapper files to the container
COPY mvnw .
COPY .mvn .mvn

# Copy the POM file to the container
COPY pom.xml .

# Resolve Maven dependencies (downloads dependencies into Docker cache)
RUN ./mvnw dependency:go-offline

# Copy the application source code to the container
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create final Docker image
FROM eclipse-temurin:17-jre-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build-image /to-build-app/target/*.jar app.jar

# Expose the port that the application listens on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
