val rootName =  "cvsfile-writer"

lazy val root = (project in file(".")).settings(
  name := rootName,
  inThisBuild(
    List(
      scalaVersion := "2.12.7",
      version := "1.0.0-SNAPSHOT"
    )
  )
).aggregate(
  example0
)

lazy val example0 = project.settings(
  name := s"$name-example0",
  libraryDependencies ++= (
    Seq(
      "com.opencsv" % "opencsv" % "4.2"
    )
  )
)
