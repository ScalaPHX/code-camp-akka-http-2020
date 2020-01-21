ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.1"

name := "complaint-service"

val akkaHttpVersion = "10.1.11"
val akkaStreamVersion = "2.5.26"
val scalaTestVersion = "3.1.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-http-testkit"    % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"          % akkaStreamVersion,
  "com.typesafe.akka"          %% "akka-stream-testkit"  % akkaStreamVersion,
  "ch.qos.logback"             % "logback-classic"       % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"        % "3.9.2",
  "org.scalactic"              %% "scalactic"            % scalaTestVersion,
  "org.scalatest"              %% "scalatest"            % scalaTestVersion % Test,
  "org.scalamock"              %% "scalamock"            % "4.4.0" % Test
)
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerBaseImage := "adoptopenjdk:13.0.1_9-jdk-openj9-0.17.0-bionic"
dockerExposedPorts ++= Seq(8080)
