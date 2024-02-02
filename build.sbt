ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version      := "0.2.0"
ThisBuild / organization := "org.ghutchis"

val chiselVersion = "3.6.0"

lazy val root = (project in file(".")).settings(
  name := "bigsur",
  libraryDependencies ++= Seq(
    "edu.berkeley.cs" %% "chisel3" % chiselVersion,
    "edu.berkeley.cs" %% "chiseltest" % "0.6.2" % "test",
  ),

  scalacOptions ++= Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    "-Ymacro-annotations"
  ),
  addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion cross CrossVersion.full),
)

Compile / mainClass := Some("bigsur.generate.Generate")
