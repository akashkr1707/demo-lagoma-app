package com.example.demoappstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.demoappstream.api.DemoappStreamService
import com.example.demoapp.api.DemoappService
import com.softwaremill.macwire._

class DemoappStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new DemoappStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new DemoappStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[DemoappStreamService])
}

abstract class DemoappStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[DemoappStreamService](wire[DemoappStreamServiceImpl])

  // Bind the DemoappService client
  lazy val demoappService: DemoappService = serviceClient.implement[DemoappService]
}
