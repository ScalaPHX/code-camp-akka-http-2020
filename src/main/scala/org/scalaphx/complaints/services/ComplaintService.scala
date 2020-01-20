package org.scalaphx.complaints.services

import org.scalaphx.complaints.model.Complaint
import org.scalaphx.complaints.repositories.ComplaintRepository

import scala.concurrent.Future

trait ComplaintService {
  def create(complaint: Complaint): Future[Complaint]
  def listAll: Future[Seq[Complaint]]
  def getById(id: String): Future[Option[Complaint]]
}

class ComplaintServiceImpl(complaintRepository: ComplaintRepository) extends ComplaintService {
  override def create(complaint: Complaint): Future[Complaint] = complaintRepository.save(complaint)

  override def listAll: Future[Seq[Complaint]] = complaintRepository.listAll

  override def getById(id: String): Future[Option[Complaint]] = complaintRepository.getById(id)
}
