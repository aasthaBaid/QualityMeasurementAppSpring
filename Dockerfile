FROM ubuntu:24.04
 
ARG DEBIAN_FRONTEND=noninteractive
 
# Install Java and Maven

RUN apt-get update && \

    apt-get install -y openjdk-17-jdk maven
 
# Set working directory

WORKDIR /app
 
# Copy project files from host machine into container

COPY . .
 
# Build application

RUN mvn clean package -DskipTests
 
# Expose Spring Boot port

EXPOSE 8080
 
# Run application

CMD ["java", "-jar", "target/QualityMeasurementSpring-0.0.1-SNAPSHOT.jar"]
 
