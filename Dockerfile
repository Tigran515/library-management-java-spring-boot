FROM maven:3.8.7-openjdk-18 AS maven
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18-jdk-slim
WORKDIR /app
COPY --from=maven /app/target/library-spring-boot-0.0.1-SNAPSHOT.jar library-spring-boot-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-spring-boot-app.jar"]
