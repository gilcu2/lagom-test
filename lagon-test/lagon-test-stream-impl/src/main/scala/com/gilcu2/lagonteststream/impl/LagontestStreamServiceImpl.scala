package com.gilcu2.lagonteststream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.gilcu2.lagonteststream.api.LagontestStreamService
import com.gilcu2.lagontest.api.LagontestService

import scala.concurrent.Future

/**
  * Implementation of the LagontestStreamService.
  */
class LagontestStreamServiceImpl(lagontestService: LagontestService) extends LagontestStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(lagontestService.hello(_).invoke()))
  }
}
