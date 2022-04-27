ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "scala-dojos"
  )

libraryDependencies += "org.typelevel" %% "cats-core" % "2.3.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test