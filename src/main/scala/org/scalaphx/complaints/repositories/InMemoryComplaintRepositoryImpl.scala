package org.scalaphx.complaints.repositories
import java.time.Instant
import java.util.UUID

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
    val updatedComplaint = updateComplaintWithManagedFields(complaint)
    complaints += updatedComplaint
    updatedComplaint
  }

  // ensure that the ID is set; if spray-json creates the Complaint from JSON - it won't use the default values!
  private def updateComplaintWithManagedFields(complaint: Complaint) = {
    complaint.id match {
      case Some(_) => complaint.copy(createdAt = Option(Instant.now))
      case None    => complaint.copy(id = Option(UUID.randomUUID().toString), createdAt = Option(Instant.now))
    }
  }

  override def listAll: Future[Seq[Complaint]] = Future(complaints.toSeq)

  override def getById(id: String): Future[Option[Complaint]] = Future(complaints.find(_.id.getOrElse("") == id))
}
