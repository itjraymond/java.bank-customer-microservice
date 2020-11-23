# Example bank-customer-microservice implemented in Java

Simple example of microservice implemented in Java and using Spring Boot 2.3.5.RELEASE.  The idea is to have a 
_Customer_ bounded context which all operations are exposed through a REST API.  As part of this project, we will
also develop a Client library where other microservices can import and used to interact with the _Customer_
microservice.  So this project will be a gradle multi-project:  _customer-api_ (the µservice) and _customer-client_.

## Multi Project setup: _API and Client_

When you use spring initialzr to create a new Spring Boot application, the build system you choose will create
a single project.  We will create two Spring Boot applications (despite the client is a library and not an application;
 but we will keep it simple for now).  Before we can create those Spring Boot application, we will setup a gradle 
 multi-project where each Spring Initializr will be dropped.
 
 To create the