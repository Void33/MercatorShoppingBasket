ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "shopping"
  )

Compile / run / mainClass := Some("com.steveedmans.Main")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"
