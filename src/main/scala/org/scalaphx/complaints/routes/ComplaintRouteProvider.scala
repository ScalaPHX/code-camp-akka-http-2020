package org.scalaphx.complaints.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.typesafe.scalalogging.LazyLogging
import org.scalaphx.complaints.model.Complaint
import org.scalaphx.complaints.services.ComplaintService

import scala.util.{Failure, Success}

class ComplaintRouteProvider(service: ComplaintService) extends SprayJsonSupport with LazyLogging {

  val routes = path("complaints") {
    post {
      entity(as[Complaint]) { complaint =>
        onComplete(service.create(complaint)) {
          case Success(complaint) => complete(StatusCodes.Created -> complaint)
          case Failure(ex) =>
            complete(
              StatusCodes.InternalServerError -> s"Error occurred while saving your complaint.  Please register another complaint.  Error was: ${ex.getMessage}"
            )
        }
      }
    } ~
      get {
        onComplete(service.listAll) {
          case Success(complaints) => complete(StatusCodes.OK -> complaints)
          case Failure(ex)         => complete(StatusCodes.InternalServerError -> s"Error occurred while listing all complaints.  Error was: ${ex.getMessage}")
        }
      }
  } ~
    pathPrefix("complaints" / Segment) { id =>
      get {
        onComplete(service.getById(id)) {
          case Success(maybeComplaint) =>
            maybeComplaint match {
              case Some(complaint) => complete(StatusCodes.OK -> complaint)
              case None            => complete(StatusCodes.NotFound -> s"Complaint with id: $id was not found.")
            }
          case Failure(ex) =>
            complete(StatusCodes.InternalServerError -> s"Error occurred while looking for complaint with id $id.  Error was: ${ex.getMessage}")
        }
      }
    }
}
