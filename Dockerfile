FROM maven:3.9 as build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn clean package

FROM azul/zulu-openjdk-alpine:17-jre-latest as runtime
LABEL authors="Lennart Rosam <rosam@sipgate.de>"

RUN mkdir -p /app
WORKDIR /app
COPY --from=build /usr/src/app/target/udpproxy.jar /app/udpproxy.jar

ENTRYPOINT ["java", "-jar", "/app/udpproxy.jar"]