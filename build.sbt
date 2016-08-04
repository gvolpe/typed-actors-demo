name := """typed-actors-demo"""

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-actor"           % "2.4.9-RC1",
  "de.knutwalker"     %% "typed-actors"         % "1.6.0",
  "de.knutwalker"     %% "typed-actors-creator" % "1.6.0"
)

