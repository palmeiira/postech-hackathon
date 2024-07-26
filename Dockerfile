FROM maven:3.9.2-eclipse-temurin-17 AS build
COPY . /home/app/src
WORKDIR /home/app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/app/src/target/telemedicine-0.0.1-SNAPSHOT.jar /app/telemedicine.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "telemedicine.jar"]