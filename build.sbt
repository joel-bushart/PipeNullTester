
name := "PipeNullTester"

version := "0.1"

scalaVersion := "2.12.4"

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

scalacOptions += "-Ypartial-unification"

resolvers := Seq(
  "Artifactory Realm " at "https://repo.artifacts.weather.com/sun-release-local",
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeIvyRepo("releases"),
  Resolver.bintrayRepo("iheartradio", "sbt-plugins"),
  Resolver.bintrayRepo("iheartradio", "maven")
)

libraryDependencies ++= {
  
  object version {
    val slf4s = "1.7.13"
    val logback = "1.1.2"
    val shapeless = "2.3.2"
    val circe = "0.8.0"
    val cats = "1.0.1"
  }
  
  Seq(
    // Logging
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "org.slf4j" % "log4j-over-slf4j" % version.slf4s,
    
    // Akka
    "com.typesafe.akka" %% "akka-actor" % "2.4.14",
    "com.typesafe.akka" %% "akka-slf4j" % "2.4.14",
    
    // Functional
    "com.chuusai" %% "shapeless" % version.shapeless,
    "org.typelevel" %% "cats-core" % version.cats,
    
    
    // Utils
    "com.typesafe" % "config" % "1.3.1",
    "org.apache.commons" % "commons-lang3" % "3.5",
  
    //Http
    "org.scalaj" %% "scalaj-http" % "2.3.0",
    
    // Test
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
    "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.3" % "test"
  )
  
}.map(_ withSources() withJavadoc())
