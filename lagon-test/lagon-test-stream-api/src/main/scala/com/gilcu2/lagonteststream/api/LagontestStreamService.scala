package com.gilcu2.lagonteststream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The lagon-test stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the LagontestStream service.
  */
trait LagontestStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("lagon-test-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

