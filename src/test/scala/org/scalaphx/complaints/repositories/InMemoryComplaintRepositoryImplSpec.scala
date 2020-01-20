package org.scalaphx.complaints.repositories

import org.scalaphx.complaints.model.Complaint
import org.scalatest.OptionValues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class InMemoryComplaintRepositoryImplSpec extends AnyFunSpec with Matchers with ScalaFutures with OptionValues {
  describe("InMemoryRepositoryImpl should") {
    val repository = new InMemoryComplaintRepositoryImpl
    val complaint = Complaint(topic = "Test", message = "Unit testing takes so much time...", userId = "tester@testing.com")
    it("save a complaint") {
      whenReady(repository.save(complaint)) { results =>
        results.id must be(complaint.id)
        results.createdAt must not be empty
      }
    }
    it("lists all complaints") {
      whenReady(repository.listAll) { results =>
        results must not be empty
        results.head.id must be(complaint.id)
      }
    }
    it("returns a complaint by id when it has it") {
      whenReady(repository.getById(complaint.id)) { results =>
        results must not be empty
        results.value.id must be(complaint.id)
      }
    }
    it("returns None when it can't find by id") {
      whenReady(repository.getById("NON-EXISTANT-ID")) { results =>
        results must be(None)
      }
    }
  }
}
