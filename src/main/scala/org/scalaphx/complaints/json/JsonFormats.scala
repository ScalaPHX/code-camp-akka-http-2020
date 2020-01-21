package org.scalaphx.complaints.json

import java.time.Instant
import java.time.format.DateTimeFormatter

import com.typesafe.scalalogging.LazyLogging
import spray.json._

object JsonFormats extends LazyLogging {

  implicit object InstantFormat extends RootJsonFormat[Instant] {

    override def read(json: JsValue): Instant = json match {
      case JsString(s) if s.endsWith("Z") => Instant.parse(s)
      case JsString(s) =>
        logger.warn(s"Time stamp with offset (should be UTC): $s")
        Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(s))
      case _ => deserializationError(s"Unknown Instant (ISO 8601 with UTC time zone expected): $json")
    }

    override def write(obj: Instant): JsValue = JsString(obj.toString)
  }
}
