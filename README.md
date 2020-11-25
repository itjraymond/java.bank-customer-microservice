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
$ docker run -d -p 27017:27017 -v /Users/jraymond/data/mongodb:/data/db --name bank-customer-api mongo 
```

We can stop this docker running-image with:

```css
$ docker stop bank-customer-api
```

And we can re-start our image with:

```css
$ docker start bank-customer-api
```

## Spring Boot _`bank-customer-api`_ and _`bank-customer-client`_

I used [Spring Initializr](https://start.spring.io/) to create both gradle sub-projects and unzipped each sub-project
(_`bank-customer-api`_ and _`bank-customer-client`_) at the root level.  We choose _Spring Reactive Web_ because 
 with microservices we want to avoid blocking IO. After removing the zip files, our 
multi-project structure look like:

```css
$ ls -la
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

We can then try to run the basic default application.  Make sure we are running MongoDB in our Docker container.

```js
$ ./gradlew :bank-customer-api:bootRun

> Task :bank-customer-api:bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.5.RELEASE)

2020-11-24 09:58:57.228  INFO 7359 --- [  restartedMain] c.j.b.c.CustomerApiApplication           : Starting CustomerApiApplication on snappi.hitronhub.home with PID 7359 (/Users/jraymond/workspaces/java/java.bank-customer-microservice/bank-customer-api/build/classes/java/main started by jraymond in /Users/jraymond/workspaces/java/java.bank-customer-microservice/bank-customer-api)
2020-11-24 09:58:57.230  INFO 7359 --- [  restartedMain] c.j.b.c.CustomerApiApplication           : No active profile set, falling back to default profiles: default
2020-11-24 09:58:57.266  INFO 7359 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : Devtools property defaults active! Set 'spring.devtools.add-properties' to 'false' to disable
2020-11-24 09:58:57.266  INFO 7359 --- [  restartedMain] .e.DevToolsPropertyDefaultsPostProcessor : For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
2020-11-24 09:58:57.618  INFO 7359 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Reactive MongoDB repositories in DEFAULT mode.
2020-11-24 09:58:57.634  INFO 7359 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 12ms. Found 0 Reactive MongoDB repository interfaces.
2020-11-24 09:58:58.130  INFO 7359 --- [  restartedMain] org.mongodb.driver.cluster               : Cluster created with settings {hosts=[localhost:27017], mode=SINGLE, requiredClusterType=UNKNOWN, serverSelectionTimeout='30000 ms'}
2020-11-24 09:58:58.290  INFO 7359 --- [localhost:27017] org.mongodb.driver.connection            : Opened connection [connectionId{localValue:1, serverValue:3}] to localhost:27017
2020-11-24 09:58:58.296  INFO 7359 --- [localhost:27017] org.mongodb.driver.cluster               : Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=STANDALONE, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=9, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=5344686}
2020-11-24 09:58:58.326  INFO 7359 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2020-11-24 09:58:58.367  INFO 7359 --- [  restartedMain] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port(s): 8080
2020-11-24 09:58:58.377  INFO 7359 --- [  restartedMain] c.j.b.c.CustomerApiApplication           : Started CustomerApiApplication in 1.67 seconds (JVM running for 2.051)
```

## Curl testing

After the`bank-customer-api` has been implemented (see source code) we can test
with few curl commands.

#### Get all the customers

```css
$ curl http://localhost:8080/customers/ | python -m json.tool

[
    {
        "dob": "1970-08-17",
        "firstname": "Bob",
        "id": "5fbd893a6125c772d0e03355",
        "lastname": "Cruz"
    },
    {
        "dob": "1962-07-09",
        "firstname": "Tim",
        "id": "5fbd893a6125c772d0e03356",
        "lastname": "Bazz"
    },
    {
        "dob": "1967-12-25",
        "firstname": "Santa",
        "id": "5fbd893a6125c772d0e03357",
        "lastname": "Claus"
    }
]
```

#### Get one specific customer (by id)

```css
$ curl http://localhost:8080/customers/5fbd893a6125c772d0e03356 | python -m json.tool

{
    "dob": "1962-07-09",
    "firstname": "Tim",
    "id": "5fbd893a6125c772d0e03356",
    "lastname": "Bazz"
}
```

#### Insert a new customer

```css
$ curl -d '{"id": null, "firstname": "Jimmy", "lastname": "Fortran", "dob": "1999-10-23"}' \
-H 'Content-Type: application/json' \
http://localhost:8080/customers/

> {"id":"5fbd8ab36125c772d0e03358","firstname":"Jimmy","lastname":"Fortran","dob":"1999-10-23"}
```

#### Update an existing customer

```css
$ curl -d '{"id": "5fbd8ab36125c772d0e03358", "firstname": "Jammy", "lastname": "Fortrain", "dob": "1999-10-23"}' \
-H 'Content-Type: application/json' \
-X PUT http://localhost:8080/customers/55fbd8ab36125c772d0e03358

> {"id":"5fbd8ab36125c772d0e03358","firstname":"Jammy","lastname":"Fortrain","dob":"1999-10-23"}
```

#### Delete exsiting customer

```css
$ curl -X DELETE http://localhost:8080/customers/5fbd8ab36125c772d0e03358
```