# Use a Java 17 base image
FROM eclipse-temurin:17-jdk

# Create and switch to the app directory
WORKDIR /app

# Copy all files from your repo into the container
COPY . .

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Build the JAR
RUN ./mvnw clean package -DskipTests

# Expose the port (Render automatically sets PORT, but exposing 8080 is typical)
EXPOSE 8080

# Run the JAR
CMD ["java", "-jar", "target/QED-0.0.1-SNAPSHOT.jar"]
