FROM openjdk:11-jre-slim

ARG JAR_FILE
ADD target/${JAR_FILE} /target.jar

CMD ["java", "-jar", "/target.jar"]
EXPOSE 8080