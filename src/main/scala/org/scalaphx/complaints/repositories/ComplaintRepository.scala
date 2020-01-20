package org.scalaphx.complaints.repositories

import org.scalaphx.complaints.model.Complaint

import scala.concurrent.Future

trait ComplaintRepository {
  def save(complaint: Complaint): Future[Complaint]
  def listAll: Future[Seq[Complaint]]
  def getById(id: String): Future[Option[Complaint]]
}
