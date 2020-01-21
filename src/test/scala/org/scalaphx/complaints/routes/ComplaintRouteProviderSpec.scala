package org.scalaphx.complaints.routes

import java.time.Instant

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalaphx.complaints.model.Complaint
import org.scalaphx.complaints.services.ComplaintService
import org.scalatest.OptionValues
import org.scalatest.matchers.must.Matchers
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.Future

class ComplaintRouteProviderSpec extends AnyFunSpec with Matchers with OptionValues with ScalatestRouteTest with MockFactory with SprayJsonSupport {
  private val mockComplaintService = mock[ComplaintService]
  private val routeProvider = new ComplaintRouteProvider(mockComplaintService)
  private val complaint = Complaint(topic = "Test", message = "Unit testing takes so much time...", userId = "tester@testing.com")
  private val expectedCreateResults = complaint.copy(createdAt = Option(Instant.now()))

  private def setCreateExpectations = {
    (mockComplaintService.create _).expects(*).returning(Future(expectedCreateResults))
  }

  private def setListAllExpectations = {
    (mockComplaintService.listAll _: () => Future[Seq[Complaint]]).expects().returning(Future(Seq(complaint, complaint, complaint)))
  }

  private def setGetByIdExpectations = {
    (mockComplaintService.getById _).expects(*).returning(Future(Option(complaint)))
  }

  describe("ComplaintRoutes should accept") {
    it("POST /complaints with a valid payload") {
      setCreateExpectations
      Post("/complaints", complaint) ~> routeProvider.routes ~> check {
        status must be(StatusCodes.Created)
        responseAs[Complaint].createdAt must not be empty
      }
    }
    it("GET /complaints") {
      setListAllExpectations
      Get("/complaints") ~> routeProvider.routes ~> check {
        status must be(StatusCodes.OK)
        responseAs[Seq[Complaint]] must not be empty
      }
    }
    it("GET /complaints/<id>") {
      setGetByIdExpectations
      Get(s"/complaints/${complaint.id}") ~> routeProvider.routes ~> check {
        status must be(StatusCodes.OK)
        responseAs[Option[Complaint]] must not be empty
      }
    }
  }
}
