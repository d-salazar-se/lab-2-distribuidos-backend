package models

import play.api.libs.json.Json
import play.api.data._
import play.api.data.Forms._

case class Word(value: String)

object JsonWordFormats {
  implicit val wordFormat = Json.format[Word]
}
