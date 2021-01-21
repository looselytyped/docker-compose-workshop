FROM openjdk:11.0.5-jre

RUN apt-get update \
  && apt-get install -y --no-install-recommends \
  netcat=1.10-41  \
  procps=2:3.3.12-3+deb9u1 \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

COPY ./build/libs/docker-compose-workshop-0.0.1-SNAPSHOT-fat.jar /var/app.jar

CMD ["java", "-jar", "/var/app.jar"]
