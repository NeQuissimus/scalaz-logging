import Scalaz._

val kindprojector = compilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")
val silencer      = compilerPlugin("com.github.ghik" %% "silencer-plugin" % "1.2")
val sourcecode    = "com.lihaoyi" %% "sourcecode" % "0.1.4"
val scalaz        = "org.scalaz" %% "scalaz-core" % "7.2.26"
val zio           = "org.scalaz" %% "scalaz-zio" % "0.2.10"

resolvers in ThisBuild += "Sonatype OSS Staging".at(
  "https://oss.sonatype.org/content/repositories/staging"
)
organization in ThisBuild := "org.scalaz"
version in ThisBuild := "0.1.0-SNAPSHOT"
publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots".at(nexus + "content/repositories/snapshots"))
  else
    Some("releases".at(nexus + "service/local/staging/deploy/maven2"))
}
scalaVersion in ThisBuild := "2.12.7"

dynverSonatypeSnapshots in ThisBuild := true

lazy val sonataCredentials = for {
  username <- sys.env.get("SONATYPE_USERNAME")
  password <- sys.env.get("SONATYPE_PASSWORD")
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)

credentials in ThisBuild ++= sonataCredentials.toSeq

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

lazy val root =
  (project in file("."))
    .settings(
      skip in publish := true
    )
    .aggregate(logCore)

lazy val logCore = (project in file("core"))
  .settings(stdSettings("logging-core"))
  .settings(
    libraryDependencies ++= Seq(silencer, sourcecode, scalaz)
  )
