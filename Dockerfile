FROM --platform=$TARGETOS/$TARGETARCH eclipse-temurin:23-jre-alpine

WORKDIR /usr/app
COPY target/backend-*-app/lib lib
COPY --chmod=755 target/backend-*-app/bin/backend bin/backend

ENTRYPOINT ["/usr/app/bin/backend"]
