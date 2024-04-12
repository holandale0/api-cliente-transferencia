FROM openjdk:17-jdk-alpine

ENV TZ=America/Sao_Paulo

RUN apk add --no-cache tzdata

RUN mkdir /app

WORKDIR /app

COPY target/api-cliente-transferencia-*.jar /app/application.jar

EXPOSE 8084

CMD ["java", "-jar", "application.jar"]
