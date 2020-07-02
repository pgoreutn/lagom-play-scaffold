import sbt._

object Versions {

  val slf4jApiVersion             = "1.7.28"
  val playJsonVersion             = "2.7.4"
  val playSlickVersion            = "3.0.0"
  val akkaDiscoveryEC2Version     = "1.0.5"
  val macwireVersion              = "2.3.3"
  val scalaGuiceVersion           = "4.2.6"
  val postgresDriverVersion       = "42.1.4"
  val scalatestVersion            = "3.1.1"
  val gatlingVersion              = "3.1.0"
  val kamonVersion                = "2.1.0"
}


object Dependencies {
  import Versions._

  val slf4jApi                    = "org.slf4j"                     % "slf4j-api"                                       % slf4jApiVersion
  val playJson                    = "com.typesafe.play"            %% "play-json"                                       % playJsonVersion
  val playSlick                   = "com.typesafe.play"            %% "play-slick"                                      % playSlickVersion
  val akkaDiscoveryEC2            = "com.lightbend.akka.discovery" %% "akka-discovery-aws-api"                          % akkaDiscoveryEC2Version
  val macwire                     = "com.softwaremill.macwire"     %% "macros"                                          % macwireVersion          % Provided
  val scalaGuice                  = "net.codingwell"               %% "scala-guice"                                     % scalaGuiceVersion
  val postgresDriver              = "org.postgresql"                % "postgresql"                                      % postgresDriverVersion
  val kamonBundle                 = "io.kamon"                     %% "kamon-bundle"                                    % kamonVersion
  val scalaTest                   = "org.scalatest"                %% "scalatest"                                       % scalatestVersion        % Test
  val gatlingCharts               = "io.gatling.highcharts"         % "gatling-charts-highcharts"                       % gatlingVersion          % Test
  val gatling                     = "io.gatling"                    % "gatling-test-framework"                          % gatlingVersion          % Test
}
