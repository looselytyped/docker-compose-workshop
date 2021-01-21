# Vertx in a container with a MongoDB backend

This is a simple project with a Vert.x frontend and a MongoDB backend.

## Pre-requisites

You will need Java 8 installed to run this application.
If you have to run a different version of Java for work (or personal) reasons, look at a tool like [Sdkman](https://sdkman.io/).

## Building

You will first need to build the project, and a resultant `shadowJar`
(or `fatJar`).
This project uses Gradle as its build system.
To build the project simply run the following **in this directory**

```
./gradlew compileJava shadowJar;
```

This might take a while if you do not have the right version of
Gradle, causing Gradle to self-install prior to running the build.

You can run this as a regular application (jar) file with

``` shell
./gradlew run;
```

## Missing dependencies

Then visit <http://localhost:8080/index.hbs> to see the application.

While this application does wake up, it *is* looking for a mongodb
back-end and will eventually report a `ConnectionException`.

You can see this by simply visiting
<http://localhost:8080/friends.hbs> and you notice we have no Contacts
:(

Now we can always install MongoDB on our machines, but why do that
when we have Docker? :)

## Running the application (and its dependencies) in Docker containers

### Manually

We start by packaging the Java runtime and our application in a
container.
This project contains a `Dockerfile` that we can use to build this
image.
**Ensure that you have built this project as described under the
"Building" section first!**
`cd` to the directory containing this file, then run the following:

```shell
./gradlew shadowJar;
docker build --tag vertx-app .;
```

First, let us create a network

```shell
docker network create my-net
```

```shell
docker run --rm --name mongo -d --network my-net  mongo:3.4.5;
# ensure it started up
docker ps;
```

`docker ps` should report the `mongo` "IMAGE" running.
**Note** that we "named" our mongo container `mongo` which is the DNS name our application uses to connect to it.

Next we can start up our application in a container, linked to the same network.

```shell
docker run --rm --name vertx-container -p 8080:8080 --network my-net vertx-app;
```

We are intentionally running our app in non-daemon mode so we can
watch the logs.
Notice that the "image" name we supply here, namely `vertx-app` is the
same name we used when we "docker build" earlier.

In a **new** terminal window:

```shell
docker ps;
```

We will now see two containers in the list; our previously started
`mongo` container, and a new container with the "IMAGE" `vertx-app`
listening on "PORTS" `8080`.

Now you should be able to visit <http://localhost:8080/index.hbs> and
follow the "Friends" link to see all our friends!

### Using docker compose

This project also includes a `docker-compose.yml` file that mimics
what we did manually.
In other words it uses the mongo image to create a mongo DB running in
a container and listening at a certain port; it then uses the
Dockerfile to create an image of our application (with the jre), and
then creates another container that links to the mongo container, all
in one fell swoop!

You can "compose" the set of services that build our application using
the `docker-compose` command.

We first build all the services in our composition using the following:

```shell
docker-compose build;
```

Then we can start up everything like so

```shell
docker-compose up;
```

You can combine the above two like so

```shell
docker-compose up --build;
```

You can exit out of docker-compose simply by using `Ctrl-c`.
