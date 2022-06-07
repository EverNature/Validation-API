FROM openjdk:8-jdk-alpine

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ARG JASYPT_PWD
ENV ENV_PWD=${JASYPT_PWD}

ENTRYPOINT ["java","-jar","/app.jar", "--jasypt.encryptor.password=${ENV_PWD}"]