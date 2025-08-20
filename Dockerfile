FROM debian:bookworm-slim

RUN apt-get update \
 && apt-get install -y --no-install-recommends \
      openjdk-17-jdk \
      maven \
      ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# Public/Free API-Key, so don't be scared LOL=D
ENV REQRES_API_KEY=reqres-free-v1

WORKDIR /app

COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline || true

COPY src ./src
COPY testng.xml ./testng.xml

CMD ["mvn", "clean", "test", "-Dsurefire.suiteXmlFiles=testng.xml"]