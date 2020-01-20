package org.scalaphx.complaints.model

import java.time.Instant
import java.util.UUID

case class Complaint(id: String = UUID.randomUUID().toString, userId: String, topic: String, message: String, createdAt: Option[Instant] = None)
