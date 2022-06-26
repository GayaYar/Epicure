FROM openjdk:latest
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} epicure-spring.jar
ENTRYPOINT ["java", "-jar", "application.jar"]