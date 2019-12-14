name := """alpaca"""
organization := "comacom.abhi"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies ++= Seq(
    ws,
    ehcache,
    "com.typesafe.play" %% "play-json" % "2.7.4",
    "ai.x" %% "play-json-extensions" % "0.40.2",
    "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
)
scalacOptions ++= Seq(
    "-language:postfixOps"
)