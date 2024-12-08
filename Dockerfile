# Stage 1: Build the frontend
FROM node:20 AS smen-fe
WORKDIR /app
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Stage 2: Build the backend
FROM gradle:8.5-jdk17 AS smen-be
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY --from=smen-fe /app/dist/* ./src/main/resources/static
RUN gradle clean 
COPY src ./src
RUN gradle build 

# Stage 3: Run the application
FROM openjdk:17-jdk-slim AS smen
WORKDIR /app
COPY --from=smen-be /app/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]
