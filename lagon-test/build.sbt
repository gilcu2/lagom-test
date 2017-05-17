organization in ThisBuild := "com.gilcu2"
version in ThisBuild := "0.1"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `lagon-test` = (project in file("."))
  .aggregate(`lagon-test-api`, `lagon-test-impl`, `lagon-test-stream-api`, `lagon-test-stream-impl`)

lazy val `lagon-test-api` = (project in file("lagon-test-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagon-test-impl` = (project in file("lagon-test-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`lagon-test-api`)

lazy val `lagon-test-stream-api` = (project in file("lagon-test-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagon-test-stream-impl` = (project in file("lagon-test-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`lagon-test-stream-api`, `lagon-test-api`)

