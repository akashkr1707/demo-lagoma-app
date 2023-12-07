package com.example.demoappstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.demoappstream.api.DemoappStreamService
import com.example.demoapp.api.DemoappService

import scala.concurrent.Future

/**
  * Implementation of the DemoappStreamService.
  */
class DemoappStreamServiceImpl(demoappService: DemoappService) extends DemoappStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(demoappService.hello(_).invoke()))
  }
}
