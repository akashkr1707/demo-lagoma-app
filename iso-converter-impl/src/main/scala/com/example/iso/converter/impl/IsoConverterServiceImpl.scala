package com.example.iso.converter.impl

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.{Done, NotUsed}
import com.example.iso.converter.api.IsoConverterService
//import com.example.iso.converter.impl.isoconverter.Iso8583Mediator
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future}


class IsoConverterServiceImpl()(implicit system: ActorSystem,
                                executionContext: ExecutionContext,
                                materializer: Materializer)
  extends IsoConverterService {

  private final val log: Logger =
    LoggerFactory.getLogger(classOf[IsoConverterServiceImpl])

//  override def isoConverter: ServiceCall[NotUsed, Done] = { _ =>
////    Iso8583Mediator.start()
//  }

  override def checkHealth: ServiceCall[NotUsed, String] = ServiceCall { _ =>
    Future.successful("Fine")
  }
}
