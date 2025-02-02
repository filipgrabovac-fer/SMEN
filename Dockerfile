# Stage 1: Build the frontend
FROM node:20 AS smen-fe
WORKDIR /app
COPY src ./src
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build


# Stage 2: Build the backend
FROM gradle:8.5-jdk17 AS smen-be
WORKDIR /app
COPY --from=smen-fe /app/src ./src
COPY build.gradle settings.gradle gradlew ./
RUN gradle build 

# Stage 3: Run the application
FROM openjdk:17-jdk AS smen
WORKDIR /app
COPY --from=smen-be /app/build/libs/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "./app.jar"]