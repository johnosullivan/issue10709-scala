name := "issue10709-scala"

version := "0.1"

scalaVersion := "2.12.6"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"       % "3.0.5"  % Test,
  "org.mockito"   %  "mockito-inline"  % "2.18.3" % Test,
  "com.novocode"  %  "junit-interface" % "0.11"   % Test,
  "org.mockito"   %  "mockito-inline"  % "2.18.3" % Test
)

enablePlugins(JavaAppPackaging)
