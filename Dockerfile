FROM --platform=$TARGETOS/$TARGETARCH eclipse-temurin:23-jre-alpine

WORKDIR /usr/app
COPY target/backend-*-app .

ENTRYPOINT ["/usr/app/bin/backend"]
