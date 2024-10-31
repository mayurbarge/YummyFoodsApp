import play.sbt.PlayImport.ws

import scala.collection.Seq

ThisBuild / name := """yummyfoodsapp"""
ThisBuild / organization := "com.yummyfoods"
ThisBuild / scalaVersion := "2.13.14"
ThisBuild / version := "1.0-SNAPSHOT"

lazy val YummyFoodsApp =
  (project in file("."))
    .enablePlugins(PlayScala)
    .settings(
      libraryDependencies ++= Seq(
        guice,
        ws,
        ehcache,
        "org.gnieh" %% "fs2-data-csv" % "1.11.1",
        "org.gnieh" %% "fs2-data-csv-generic" % "1.11.1",
        "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test)

    )
    .settings(
      libraryDependencies ++= Seq(
        "org.reactivemongo" % "reactivemongo_2.13" % "1.1.0.pekko-RC12"
      )
    )
    .dependsOn(domain)
    .aggregate(domain)

lazy val domain =
  project
    .in(file("domain"))
    .settings(commonSettings)

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "2.7.0",
    "org.typelevel" %% "cats-effect" % "3.3.5"
  ),
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings"
  ),
  Compile / scalaSource := baseDirectory.value / "src/main/scala",
  Test / scalaSource := baseDirectory.value / "src/test/scala",
  javaOptions in Test += "-Dconfig.file=conf/application.test.conf",
  Test / console / scalacOptions := (Compile / console / scalacOptions).value
)
  // Use sbt default layout
  //.disablePlugins(PlayLayoutPlugin)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.yummyfoods.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.yummyfoods.binders._"
