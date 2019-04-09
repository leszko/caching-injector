FROM alpine:3.8

RUN echo "Updating Alpine system" \
    && apk upgrade --update-cache --available \
    && echo "Installing new APK packages" \
    && apk add openjdk8-jre bash curl procps nss

ARG JAR_FILE=build/libs/gs-rest-service-0.1.0.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar","--server.port=80"]