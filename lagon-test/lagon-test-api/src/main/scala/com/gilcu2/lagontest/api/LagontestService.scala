package com.gilcu2.lagontest.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * The lagon-test service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the LagontestService.
  */
trait LagontestService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def hello(id: String): ServiceCall[NotUsed, String]

  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
    * "Hi"}' http://localhost:9000/api/hello/Alice
    */
  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]

  def uploadDiscountsCsv:ServiceCall[CSVMessage,Done]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("lagon-test").withCalls(
      pathCall("/api/hello/:id", hello _),
      pathCall("/api/hello/:id", useGreeting _),
      restCall(Method.POST,   "/uploadDiscountsCsv", uploadDiscountsCsv _)
    ).withAutoAcl(true)
    // @formatter:on
  }
}

/**
  * The greeting message class.
  */
case class GreetingMessage(message: String)

object GreetingMessage {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}

case class CSVMessage(country:String,csv:String)

object CSVMessage {
  implicit val format: Format[CSVMessage] = Json.format[CSVMessage]
}

case class Discount(id:String,discount: String)

object Discount {
  implicit val format: Format[Discount] = Json.format[Discount]
}