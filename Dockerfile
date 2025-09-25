# syntax=docker/dockerfile:1

############################
# Build stage
############################
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# If you use Maven Wrapper, keep these; otherwise replace with plain 'mvn'
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml .

# make wrapper executable + fix CRLF just in case
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Pre-fetch dependencies to leverage layer caching (non-fatal if wrapper plugins aren't cached yet)
RUN ./mvnw -q -DskipTests dependency:go-offline || true

# Copy sources and build
COPY src src
RUN ./mvnw -q -DskipTests package

############################
# Runtime stage
############################
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Run as non-root user for better security
RUN useradd -r -u 1001 spring && chown -R spring:spring /app
USER spring

# Copy the fat jar
COPY --from=build /app/target/*.jar app.jar

# Container-friendly JVM defaults
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseZGC -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8080
ENTRYPOINT ["/bin/sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
