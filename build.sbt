ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.1"

name := "complaint-service"

val akkaHttpVersion = "10.1.11"
val akkaStreamVersion = "2.5.26"

libraryDependencies ++= Seq(
  "com.typesafe.akka"          %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-http-testkit"    % akkaHttpVersion,
  "com.typesafe.akka"          %% "akka-stream"          % akkaStreamVersion,
  "com.typesafe.akka"          %% "akka-stream-testkit"  % akkaStreamVersion,
  "ch.qos.logback"             % "logback-classic"       % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"        % "3.9.2",
  "org.scalactic"              %% "scalactic"            % "3.1.0",
  "org.scalatest"              %% "scalatest"            % "3.1.0" % Test,
  "org.scalamock"              %% "scalamock"            % "4.4.0" % Test
)
