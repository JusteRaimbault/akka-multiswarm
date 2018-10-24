organization := "org"

name := "akka-multiswarm"

version := "0.1"

//scalaVersion := "2.10.6"
scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.10.4")

libraryDependencies ++= Seq(
  //"com.typesafe.akka" %% "akka-actor" % "2.3.2",
  "com.typesafe.akka" %% "akka-actor" % "2.5.17",
  //"com.typesafe.akka" %% "akka-testkit" % "2.3.2",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.17",
  //"org.scalanlp" % "breeze_2.10" % "0.13.1",
  "org.scalanlp" %% "breeze" % "0.13.2",
  // native libraries are not included by default. add this if you want them (as of 0.7)
  // native libraries greatly improve performance, but increase jar sizes.
  "org.scalanlp" %% "breeze-natives" % "0.13.2",
  //"org.specs2" %% "specs2" % "2.3.12" % "test"
  "org.specs2" %% "specs2-core" % "3.8.9" % "test"
  //"org.mockito" % "mockito-core" % "1.9.5")
)

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"