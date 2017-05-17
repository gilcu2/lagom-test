package com.gilcu2.lagontest.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.gilcu2.lagontest.api.LagontestService
import com.softwaremill.macwire._

class LagontestLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagontestApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagontestApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[LagontestService]
  )
}

abstract class LagontestApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LagontestService](wire[LagontestServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = LagontestSerializerRegistry

  // Register the lagon-test persistent entity
  persistentEntityRegistry.register(wire[LagontestEntity])
}
