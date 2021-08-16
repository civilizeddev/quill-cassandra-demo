package io.elfinos.cassandra

import io.getquill.CassandraAsyncContext
import io.getquill.SnakeCase
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.RemoteEndpointAwareJdkSSLOptions
import com.datastax.driver.core.ConsistencyLevel
import com.datastax.driver.core.QueryOptions
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import nl.altindag.ssl.SSLFactory

object Main extends App {

  val sslContext = SSLFactory
    .builder()
    .withDefaultTrustMaterial()
    .build()
    .getSslContext()

  val sslOptions = RemoteEndpointAwareJdkSSLOptions
    .builder()
    .withSSLContext(sslContext)
    .build()

  val cluster = Cluster
    .builder()
    .addContactPoints("<host>")
    .withPort(9999)
    .withSSL(sslOptions)
    .withCredentials("<client_id>", "<client_secret>")
    .withoutMetrics()
    .withoutJMXReporting()
    .withMaxSchemaAgreementWaitSeconds(1)
    .withQueryOptions(new QueryOptions().setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM))
    .build()

  lazy val ctx = new CassandraAsyncContext(SnakeCase, cluster, "<keyspace>", 1_000)

  val system = ActorSystem[Nothing](
    Behaviors.setup[Nothing] { context =>
      context.log.info("Hello, World!")
      Behaviors.empty
    },
    "cassandra-demo"
  )

  import system.executionContext
  import ctx._

  val q = quote {
    query[Employee].insert(_.empId -> 11, _.empName -> "David Lee", _.empAge -> 35).ifNotExists
  }

  for (_ <- ctx.run(q)) {
    println("Inserted.")
  }
}
