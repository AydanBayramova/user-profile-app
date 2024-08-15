FROM openjdk:22

# Copy the jar file into the container
COPY build/libs/bff-0.0.1-SNAPSHOT.jar app.jar


# Expose the port the app runs on
EXPOSE 8084

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]