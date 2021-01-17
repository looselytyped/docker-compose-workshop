# Docker workshop

## Highlights

- This **is a workshop**. Please come with a laptop that has the necessary installed software.
- Don't let the length of this document intimidate you :)
- Please follow **all of the installation instructions** in this document before coming to the workshop.
  Debugging docker/git/compose installation takes time away from all attendees.

## Installation

You will need **2** things installed

- Docker
- [Git](https://git-scm.com/downloads)
- [Docker Compose](https://docs.docker.com/compose/install/)

Optionally, a good text editor.
I highly recommend [VS Code](https://code.visualstudio.com/) with the [Docker Extension](https://marketplace.visualstudio.com/items?itemName=PeterJausovec.vscode-docker).

### Docker installation

- For modern OS'es you can find download instructions [here](https://store.docker.com/search?offering=community&type=edition)
- If your OS version is **not** supported by the native clients then you will need to install `docker toolbox` - https://docs.docker.com/toolbox/overview/

There are two items of note

- Please treat this installation like any other. Different operating systems and different set ups (especially in company-issued laptops) can make this installation tricky. Google, and perhaps your desktop support team (if using a company-issued laptop) are your friends. Debugging this can be tricky.
- Docker requires a slew of permissions to run. So please make sure you grant Docker **all** the permissions it needs

### Testing your installation

```bash
> docker -v
Docker version 20.10.2, build 2291f61
> docker-compose -v
docker-compose version 1.27.4, build 40524192
```

## Warm up your engines!

**You want to do this BEFORE you show up for the workshop!!**
Running this over a hotel wifi connection might not go well.
Using the command (bash) prompt:

```bash
docker pull alpine:3.12;
docker pull jenkins/jenkins:2.99;
docker pull mongo:3.6.17;
docker pull nginx:1.19.6-alpine;
docker pull openjdk:8u131-jdk;
docker pull openjdk:8u131-jre;
docker pull portainer/portainer:latest;
docker pull tomcat:9;
docker pull ubuntu:21.04;
```

## The final test

Once again, at the command prompt:

```bash
> docker run -d --name myjenkins -p 8080:8080 jenkins/jenkins:2.99;
> docker logs -f myjenkins;
```

You should see the Jenkins logs flowing by eventually settling with the following:

```
Sep 18, 2018 11:38:19 PM hudson.model.UpdateSite updateData
INFO: Obtained the latest update center data file for UpdateSource default
Sep 18, 2018 11:38:19 PM hudson.WebAppMain$3 run
INFO: Jenkins is fully up and running
Sep 18, 2018 11:38:19 PM hudson.model.UpdateSite updateData
INFO: Obtained the latest update center data file for UpdateSource default
Sep 18, 2018 11:38:20 PM hudson.model.DownloadService$Downloadable load
INFO: Obtained the updated data file for hudson.tasks.Maven.MavenInstaller
Sep 18, 2018 11:38:20 PM hudson.model.DownloadService$Downloadable load
INFO: Obtained the updated data file for hudson.tools.JDKInstaller
Sep 18, 2018 11:38:20 PM hudson.model.AsyncPeriodicWork$1 run
INFO: Finished Download metadata. 7,798 ms
--> setting agent port for jnlp
--> setting agent port for jnlp... done
```

Visit http://localhost:8080 and see if you see the Jenkins login page.
If you do, you are all set to go!!

```bash
# ctrl-c to break out of the logs
docker container stop myjenkins;
docker container rm myjenkins;
```

Woot!!!
See you soon!!!

## Notes

- This project consists of a slew of Dockerfiles that demonstrate how to (and **not** to) use a few select Dockerfile instructions
- This project also packages the artifact that this project produces so you do **not** need Java installed
