package com.itea.task1

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import com.itea.task1.controller.HealthController
import com.itea.task1.dao.HealthDao
import com.itea.task1.service.HealthService
import com.softwaremill.macwire.wire
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}

object HealthApp extends App {
  val config: Config = ConfigFactory.load().getConfig("config.rest")

  implicit val system: ActorSystem = ActorSystem("akka-http")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val dao = wire[HealthDao]
  implicit val service = wire[HealthService]
  implicit val controller = wire[HealthController]

  val port = config.getString("port").toInt
  val bindingFuture = Http()
    .bindAndHandle(
      controller.doGet,
      config.getString("interface"),
      port)

  implicit val log = Logging(system, "HealthApp$")
  log.info(s"Server started at the port $port")

}
