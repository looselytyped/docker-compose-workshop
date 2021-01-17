FROM adoptopenjdk/openjdk11:x86_64-ubuntu-jre-11.0.2.9

RUN apt-get update \
  && apt-get install -y \
  netcat  \
  && apt-get clean

EXPOSE 8080

COPY ./build/libs/docker-workshop-0.0.1-SNAPSHOT-fat.jar /var/app.jar

CMD ["java", "-jar", "/var/app.jar"]
