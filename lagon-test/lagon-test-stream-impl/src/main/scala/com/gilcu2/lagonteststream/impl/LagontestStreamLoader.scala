package com.gilcu2.lagonteststream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.gilcu2.lagonteststream.api.LagontestStreamService
import com.gilcu2.lagontest.api.LagontestService
import com.softwaremill.macwire._

class LagontestStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagontestStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagontestStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[LagontestStreamService]
  )
}

abstract class LagontestStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LagontestStreamService](wire[LagontestStreamServiceImpl])

  // Bind the LagontestService client
  lazy val lagontestService = serviceClient.implement[LagontestService]
}
