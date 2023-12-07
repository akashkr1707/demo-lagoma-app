ThisBuild / organization := "com.example"
ThisBuild / version := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
ThisBuild / scalaVersion := "2.13.8"

// Workaround for scala-java8-compat issue affecting Lagom dev-mode
// https://github.com/lagom/lagom/issues/3344
ThisBuild / libraryDependencySchemes +=
  "org.scala-lang.modules" %% "scala-java8-compat" % VersionScheme.Always

val macwire = "com.softwaremill.macwire" %% "macros" % "2.5.8" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.15" % Test
lazy val akkaVersion = "2.0.0"


//val typedActor = "com.typesafe.akka" %% "akka-actor-typed" % "2.7.0"
//val akkaStream =  "com.typesafe.akka" %% "akka-stream" % "2.7.0"
//val scodecBits =   "org.scodec" %% "scodec-bits" % "1.1.37"
//val scodecCore =  "org.scodec" %% "scodec-core" % "1.11.10"
//val catsCore = "org.typelevel" %% "cats-core" % "2.9.0"
//val catsEffect = "org.typelevel" %% "cats-effect" % "3.4.11"
//val playJson =  "com.typesafe.play" %% "play-json" % "2.9.4"
//val playJsonJoda =  "com.typesafe.play" %% "play-json-joda" % "2.9.4"
//val akkaHttp =  "com.typesafe.akka" %% "akka-http"   % "10.5.0"
//val jpos =  "org.jpos" % "jpos" % "2.1.7"
//val akkaTestKit =  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
//val slf4j =  "com.typesafe.akka" %% "akka-slf4j" % "2.4.20"
//val classicLogback =   "ch.qos.logback" % "logback-classic" % "1.4.7"

lazy val `demo-app` = (project in file("."))
  .aggregate(`demo-app-api`, `demo-app-impl`,`iso-converter-api`,`iso-converter-api`, `demo-app-stream-api`, `demo-app-stream-impl`)

lazy val `demo-app-api` = (project in file("demo-app-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `demo-app-impl` = (project in file("demo-app-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`demo-app-api`)


lazy val `iso-converter-api` = (project in file("iso-converter-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomScaladslPersistenceCassandra
//      playJsonJoda
    )
  )

lazy val `iso-converter-impl` = (project in file("iso-converter-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
//        typedActor,
//      akkaStream,
//      scodecBits,
//      scodecCore,
//      catsCore,
//      catsEffect,
//      playJson,
//      playJsonJoda,
//      akkaHttp,
//      jpos,
//      scalaTest,
//      slf4j,
//      classicLogback,
      guice
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`iso-converter-api`)

lazy val `demo-app-stream-api` = (project in file("demo-app-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `demo-app-stream-impl` = (project in file("demo-app-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`demo-app-stream-api`, `demo-app-api`)
