
organizationName := "com.briefscala"

name := "weblog-app"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0"
  ,"com.chuusai" %% "shapeless" % "2.3.0"
  ,"org.apache.spark" %% "spark-core" % "2.1.0" //% "provided"
  ,"org.apache.spark" %% "spark-mllib" % "2.1.0" //% "provided"
  ,"org.slf4j" % "slf4j-api" % "1.7.21"
  ,"joda-time" % "joda-time" % "2.9.7"
  ,"org.joda" % "joda-convert" % "1.8"
  ,"ch.qos.logback" % "logback-classic" % "1.1.3"
)

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class")
  => MergeStrategy.last
  case x => (assemblyMergeStrategy in assembly).value(x)
}

scalacOptions ++= Seq(
  "-unchecked",
  "-Xlint",
  "-deprecation",
  "-target:jvm-1.7",
  "-encoding", "UTF-8",
  "-Ywarn-dead-code",
  "-language:_",
  "-feature"
)

assemblyJarName in assembly := "weblog-app.jar"