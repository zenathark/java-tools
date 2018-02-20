import sbt.Keys._

lazy val root =
  project
    .in(file("."))
    .enablePlugins(JmhPlugin)
    .settings(
      name := "Java Personal Tools",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := "2.12.3",
      javaOptions ++= Seq("-XX:PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining")
    )

javaSource in Compile := baseDirectory.value / "src/main/java"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"
