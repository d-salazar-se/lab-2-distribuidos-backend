package models

import play.api.libs.json.Json
import play.api.data._
import play.api.data.Forms._

case class Tweet(user: String, text: String)

object JsonTweetFormats {
  implicit val wordFormat = Json.format[Tweet]
}
