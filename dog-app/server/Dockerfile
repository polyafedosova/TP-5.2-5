FROM maven:3.8.1-openjdk-17 AS builder
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
WORKDIR "/app"
COPY --from=builder /target/*.jar /dogapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/dogapp-0.0.1-SNAPSHOT.jar"]