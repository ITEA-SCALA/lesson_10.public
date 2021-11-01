package com.itea.task1.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import com.itea.task1.HealthApp.system
import com.itea.task1.dto.HealthDto
import com.itea.task1.service.HealthService
import spray.json.DefaultJsonProtocol._
import scala.util.Success

class HealthController(implicit service: HealthService) {
  implicit val log = Logging(system, "HealthController$")

  val doGet: Route =
    path("health") {
      get {
        val dto = service.get
//        onComplete( dto ) {
//          _ match {
//            case Success(dto) =>
              log.info(s"Server get health $dto")
              implicit val dtoJson = jsonFormat2( HealthDto.apply )
              complete( StatusCodes.OK, dto.toString ) //TODO  http://localhost:8080/health  |  { label: 'health-1', status: true }
//          }
//        }
      }
    }
}