package org.scalaphx.complaints.services

import java.time.Instant

import org.scalamock.scalatest.MockFactory
import org.scalaphx.complaints.model.Complaint
import org.scalaphx.complaints.repositories.ComplaintRepository
import org.scalatest.OptionValues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ComplaintServiceImplSpec extends AnyFunSpec with Matchers with ScalaFutures with OptionValues with MockFactory {
  private val mockComplaintRepo = mock[ComplaintRepository]
  private val service = new ComplaintServiceImpl(mockComplaintRepo)
  private val complaint = Complaint(topic = "Test", message = "Unit testing takes so much time...", userId = "tester@testing.com")
  private val expectedCreateResults = complaint.copy(createdAt = Option(Instant.now()))

  private def setCreateExpectations = {
    (mockComplaintRepo.save _).expects(*).returning(Future(expectedCreateResults))
  }

  private def setListAllExpectations = {
    (mockComplaintRepo.listAll _: () => Future[Seq[Complaint]]).expects().returning(Future(Seq(complaint, complaint, complaint)))
  }

  private def setGetByIdExpectations = {
    (mockComplaintRepo.getById _).expects(*).returning(Future(Option(complaint)))
  }

  describe("ComplaintServiceImpl should") {
    it("create a new Complaint") {
      setCreateExpectations
      whenReady(service.create(complaint)) { results =>
        results must be(expectedCreateResults)
      }
    }
    it("list all complaints") {
      setListAllExpectations
      whenReady(service.listAll) { results =>
        results must not be empty
        results.size must be(3)
      }
    }
    it("finds a complaint by id") {
      complaint.id.fold(fail("Missing complaint id!")) { id =>
        setGetByIdExpectations
        whenReady(service.getById(id)) { results =>
          results must not be empty
          results.value.id.value must be(id)
        }
      }
    }
  }
}
