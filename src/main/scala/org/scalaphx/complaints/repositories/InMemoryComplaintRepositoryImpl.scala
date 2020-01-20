package org.scalaphx.complaints.repositories
import java.time.Instant

import org.scalaphx.complaints.model.Complaint

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * In-memory implementation of the ComplaintRepository.
  *
  * Stores all complaints in a mutable ListBuffer.
  */
class InMemoryComplaintRepositoryImpl extends ComplaintRepository {
  private val complaints: ListBuffer[Complaint] = new ListBuffer()

  override def save(complaint: Complaint): Future[Complaint] = Future {
    val updatedComplaint = complaint.copy(createdAt = Option(Instant.now))
    complaints += updatedComplaint
    updatedComplaint
  }

  override def listAll: Future[Seq[Complaint]] = Future(complaints.toSeq)

  override def getById(id: String): Future[Option[Complaint]] = Future(complaints.find(_.id == id))
}
