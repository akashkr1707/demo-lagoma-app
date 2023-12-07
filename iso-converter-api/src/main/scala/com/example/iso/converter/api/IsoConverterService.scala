package com.example.iso.converter.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait IsoConverterService extends Service {
//  def isoConverter: ServiceCall[NotUsed, Done]

  def checkHealth: ServiceCall[NotUsed, String]

  def descriptor: Descriptor = {
    import Service._
    named("iso-converter")
      .withCalls(
//      restCall(Method.POST, "/iso-converter", isoConverter _),
      restCall(Method.GET, "/api/health", checkHealth _)
      ).withAutoAcl(true)
  }
}