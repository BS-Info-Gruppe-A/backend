FROM --platform=$TARGETOS/$TARGETARCH openjdk:24-jdk-bullseye

WORKDIR /usr/app
COPY target/backend-*-app/lib lib
COPY --chmod=755 target/backend-*-app/bin/backend bin/backend

ENTRYPOINT ["/usr/app/bin/backend"]
