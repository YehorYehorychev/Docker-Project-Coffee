FROM alpine

RUN apk add openjdk17

ENV PATH=$PATH:/usr/lib/jvm/java-17-openjdk/bin

WORKDIR /app

ADD MandelbrotAscii.java MandelbrotAscii.java

ENTRYPOINT javac MandelbrotAscii.java