FROM openjdk:11-jre-slim

RUN apk add --no-cache bash

ARG JAR_FILE
ADD target/${JAR_FILE} /target.jar

CMD ["java", "-jar", "/target.jar"]
EXPOSE 8080