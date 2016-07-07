enablePlugins(GatlingPlugin)

scalaVersion := "2.11.8"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies += "io.gatling.highcharts"  % "gatling-charts-highcharts"   % "2.2.2" % "test"
libraryDependencies += "io.gatling"             % "gatling-test-framework"      % "2.2.2" % "test"
libraryDependencies += "com.typesafe"           % "config"                      % "1.3.0"