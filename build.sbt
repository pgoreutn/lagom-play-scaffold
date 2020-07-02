import Dependencies._
import scalariform.formatter.preferences._

organization in ThisBuild := "org.example"
version in ThisBuild ~= (_.replace('+', '-'))

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.13.0"

lazy val `hello` = (project in file("."))
  .aggregate(`hello-api`, `hello-impl`, `hello-stream-api`, `hello-stream-impl`)

lazy val `hello-api` = (project in file("hello-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-impl` = (project in file("hello-impl"))
  .enablePlugins(LagomScala, JavaAgent)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      postgresDriver,
      lagomScaladslKafkaBroker,
      kamonBundle,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-api`)

lazy val `hello-stream-api` = (project in file("hello-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-stream-impl` = (project in file("hello-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`hello-stream-api`, `hello-api`)

lazy val `web-gateway` = (project in file("web-gateway"))
  .enablePlugins(PlayScala)
  .settings(commonSettings)
  .settings(dockerSettings)
  .settings(
    libraryDependencies ++= Seq(
      guice,
      scalaGuice,
      ehcache,
      filters,
      playSlick,
      postgresDriver,
      scalaTest
    )
  )

lazy val `load-tests` = (project in file("load-tests"))
  .enablePlugins(GatlingPlugin)
  .settings(
    libraryDependencies ++= Seq(
      gatling,
      gatlingCharts
    )
  )

lazy val `integration-tests` = (project in file("integration-tests"))
  .enablePlugins(GatlingPlugin)
  .settings(
    libraryDependencies ++= Seq(
      gatling,
      gatlingCharts
    )
  )

// Documentation for this project:
//    sbt "project docs" "~ paradox"
//    open docs/target/paradox/site/main/index.html
lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)

def dockerSettings = Seq(
  dockerUpdateLatest := true,
  dockerBaseImage := getDockerBaseImage(),
  dockerUsername := sys.props.get("docker.username"),
  dockerRepository := sys.props.get("docker.repository"),
  dockerExposedPorts := Seq(8558, 2550, 9000)
)

def getDockerBaseImage(): String = sys.props.get("java.version") match {
  case Some(v) if v.startsWith("11") => "adoptopenjdk/openjdk11"
  case _ => "adoptopenjdk/openjdk8"
}

lazy val commonSettings = Seq(
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-target:jvm-1.8",
    "-Xlog-reflective-calls",
    "-Xlint",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-deprecation",
    "-feature",
    "-language:_",
    "-unchecked"
  ),

  scalacOptions in (Compile, console) --= Seq("-Ywarn-unused", "-Ywarn-unused-import"),
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value,

  scalariformPreferences := scalariformPreferences.value
    .setPreference(AlignParameters, false)
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 90)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DoubleIndentMethodDeclaration, true)
    .setPreference(RewriteArrowSymbols, true)
    .setPreference(DanglingCloseParenthesis, Preserve)
    .setPreference(NewlineAtEndOfFile, true)
    .setPreference(AllowParamGroupsOnNewlines, true)
)

// We're using Docker Compose to manage:
//  * Persistence (PostgreSQL)
//  * Message Broker (Kafka)
// So we don't need Lagom's dev env to manage Cassandra and Kafka for us.
lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

// We're also setting specific ports for each service in local development
// So we don't need Lagom's dev env to give us a Service Locator, either.
lagomServiceLocatorEnabled in ThisBuild := false
