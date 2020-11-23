# Example bank-customer-microservice implemented in Java

Simple example of microservice implemented in Java and using Spring Boot 2.3.5.RELEASE.  The idea is to have a 
_Customer_ bounded context with all public operations exposed through a REST API.  As part of this project, we will
also develop a Client library where other microservices can import and used to interact with the _Customer_
microservice.  So this project will be a gradle multi-project:  _bank-customer-api_ (the µservice) and _bank-customer-client_.

## Multi Project setup: _API and Client_

When you use spring initialzr to create a new Spring Boot application, the build system you choose will create
a single maven/gradle project.  We will create two Spring Boot applications (despite the fact that the client is a 
library and not an application; but we will keep it simple for now).  Before we can create those Spring Boot 
application, we will setup a gradle multi-project where each Spring Initializr will be dropped.
 
We will use `gradle` as our build system and follow the multi-project setup instructions 
from [here](https://github.com/itjraymond/gradle-multi-proj-setup)


## MongoDB 

Since we intent to have our Spring Boot `bank-customer-api` app to have initial dependencies on MongoDB, Spring Boot
will try to auto configure the connection.  By default, Spring Boot will try to connect to a local MongoDB instance.
Since we do not want to pollute our local instance with a myriad of data store or other third party software,
we will have MongoDB run within Docker.

Before we can execute the `docker` command below, we may want to map the storage file to our localhost if we 
desire to keep our data between restart.  To that end, I created a local folder to hold those storeage files at:

`/Users/jraymond/data/mongodb/`

Once I have done that, I can run a MongoDB image without any additional configuration.

```css
itjraymond $ docker run -d -p 27017:27017 -v /Users/jraymond/data/mongodb:/data/db --name bank-customer-api mongo 
```

We can stop this docker image with:

```css
itjraymond $ docker stop bank-customer-api
```

And we can re-start our image with:

```css
itjraymond $ docker start bank-customer-api
```

## Spring Boot _`customer-api`_

I used [Spring Initializr](https://start.spring.io/) to create both gradle sub-project and unzipped each sub-project
(_`bank-customer-api`_ and _`bank-customer-client`_) at the root level.  After removing the zip files, our 
multi-project structure look like:

```css
itjraymond $ ls -la
total 56
drwxr-xr-x  14 jraymond  staff   448 23 Nov 12:02 .
drwxr-xr-x  20 jraymond  staff   640 22 Nov 11:00 ..
drwxr-xr-x  13 jraymond  staff   416 23 Nov 10:18 .git
-rw-r--r--   1 jraymond  staff   325 21 Nov 17:27 .gitignore
drwxr-xr-x   7 jraymond  staff   224 21 Nov 17:28 .gradle
drwxr-xr-x  10 jraymond  staff   320 23 Nov 12:02 .idea
-rw-r--r--   1 jraymond  staff  2435 23 Nov 12:02 README.md
drwxr-xr-x  11 jraymond  staff   352 21 Nov 17:36 bank-customer-api        <--
drwxr-xr-x  10 jraymond  staff   320 21 Nov 17:35 bank-customer-client     <--
-rw-r--r--   1 jraymond  staff   750 21 Nov 17:28 build.gradle
drwxr-xr-x   3 jraymond  staff    96 21 Nov 17:28 gradle
-rwxr-xr-x   1 jraymond  staff  5766 21 Nov 17:28 gradlew
-rw-r--r--   1 jraymond  staff  2763 21 Nov 17:28 gradlew.bat
-rw-r--r--   1 jraymond  staff   435 21 Nov 17:28 settings.gradle

```