package org.scalaphx.complaints.model

import java.time.Instant
import java.util.UUID

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Complaint(id: String = UUID.randomUUID().toString, userId: String, topic: String, message: String, createdAt: Option[Instant] = None)

object Complaint extends DefaultJsonProtocol {
  import org.scalaphx.complaints.json.JsonFormats._
  implicit val format: RootJsonFormat[Complaint] = jsonFormat5(Complaint.apply)
}
