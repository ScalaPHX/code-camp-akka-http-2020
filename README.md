# ScalaPHX Mini-Code Camp - Jan 2020
This repository contains the application that was built during the Jan 25th, 2020 mini-code camp.  Its broken up along
branches.  Master contains the starting point, then there is one branch for each step to build up the application.

## Application
This application is a REST API that accepts Complaints.  It demonstrates a very simple
application built with Scala and Akka HTTP.  Its also to be used as a base to introduce new concepts/technologies in future
ScalaPHX sessions.

## Project Setup
The project is setup with SBT.  It is also configured with the following SBT plugins:
* sbt-scoverage - code coverage tool
* sbt-scalafmt - code formatting tool

A default scalafmt config is provided.

## API Spec
The API will provide the following endpoints:

* POST /complaints
* GET  /complaints
* GET  /complaints/<id>

A complaint has the following structure:
```json
{
  "id" : "<UUID>",
  "userId" : "<email>",
  "topic" : "Some topic to complain about",
  "message" : "Some complaint details",
  "createdAt" : "2020-01-01T00:00:00.000Z"
}
```
When creating a complaint, the id is optional and will be created by the server.  The createdAt date will also be created
by the server before it is saved.