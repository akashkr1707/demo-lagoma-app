package com.example.iso.converter.impl

import akka.actor.ActorSystem
import com.example.iso.converter.api.IsoConverterService
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{Descriptor, ServiceLocator}
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class IsoConverterLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new IsoConverterLoaderApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new IsoConverterLoaderApplication(context) with LagomDevModeComponents

  override def describeService: Option[Descriptor] =
    Some(readDescriptor[IsoConverterService])
}

abstract class IsoConverterLoaderApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  implicit val system = ActorSystem("Iso8583Mediator")
  implicit val ec = system.dispatcher

  override lazy val lagomServer: LagomServer =
    serverFor[IsoConverterService](wire[IsoConverterServiceImpl])

  override lazy val jsonSerializerRegistry =
    IsoConverterSerializerRegistry

  lazy val isoConverterService: IsoConverterServiceImpl =
    wire[IsoConverterServiceImpl]

//  isoConverterService.isoConverter.invoke()

}
