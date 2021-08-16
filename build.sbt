ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / organization     := "io.elfinos"
ThisBuild / organizationName := "UNOMIC"
ThisBuild / javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

name := "cassandra-demo"

libraryDependencies += "com.typesafe.akka"        %% "akka-actor-typed"     % "2.6.15"
libraryDependencies += "io.getquill"              %% "quill-cassandra"      % "3.9.0"
libraryDependencies += "io.github.hakky54"         % "sslcontext-kickstart" % "6.8.0"
libraryDependencies += "ch.qos.logback"            % "logback-classic"      % "1.2.3"
libraryDependencies += "com.softwaremill.macwire" %% "macros"               % "2.3.7"  % Provided
libraryDependencies += "com.softwaremill.macwire" %% "util"                 % "2.3.7"
libraryDependencies += "com.softwaremill.macwire" %% "proxy"                % "2.3.7"
libraryDependencies += "org.scalameta"            %% "munit"                % "0.7.27" % Test
