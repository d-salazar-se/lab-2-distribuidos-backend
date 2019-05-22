package models

import play.api.libs.json.Json
import play.api.data._
import play.api.data.Forms._

case class Tweet(
	createdAt: String,
	text: String,
	userName: String,
	userScreenName: String,
	currentUserRetweetedId: Long,
	favoriteCount: Long,
	id: Long,
	retweetCount: Long,
	isFavorited: Boolean,
	isPossiblySensitive: Boolean,
	isRetweet: Boolean,
	isRetweeted: Boolean,
	isTruncated: Boolean,
	source: String
)

object JsonTweetFormats {
  implicit val wordFormat = Json.format[Tweet]
}
