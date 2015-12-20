
name := "examples"

organization := "iguana"

version := "0.1.0"

isSnapshot := true

scalaVersion := "2.11.7"

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.11",
  "iguana" %% "iguana" % "0.1.0" withSources() withJavadoc()
)
