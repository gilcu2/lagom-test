package com.gilcu2.lagontest.impl

import akka.Done
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.gilcu2.lagontest.api.{CSVMessage, Discount, LagontestService}

/**
  * Implementation of the LagontestService.
  */
class LagontestServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends LagontestService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the lagon-test entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LagontestEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the lagon-test entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LagontestEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }

  override def uploadDiscountsCsv: ServiceCall[CSVMessage, Done] = ServiceCall { msg =>
    val ref=
    val list=msg.csv.split("\n").map(_.split(",")).map(x=>Discount(x(0),x(1)))


  }

}
