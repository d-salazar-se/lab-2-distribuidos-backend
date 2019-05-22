package controllers

import play.api._
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.{JsArray, Json}
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor

import scala.concurrent.Future

object TweetController extends Controller with MongoController{

  def collection: JSONCollection = db.collection[JSONCollection]("tweets")

  import models._
  import models.JsonTweetFormats._

  def index(limit: Int) = Action.async {
    val cursor: Cursor[Tweet] = collection.find(Json.obj()).sort(Json.obj("_id" -> -1)).cursor[Tweet]

    val futureTweetsList: Future[List[Tweet]] = cursor.collect[List](limit)

    futureTweetsList.map { tweets =>
      Ok(Json.toJson(tweets))
    }
  }

  def getById(id: Long) = Action.async {
    val cursor: Cursor[Tweet] = collection.find(Json.obj("id" -> id)).cursor[Tweet]

    val futureTweetsList: Future[List[Tweet]] = cursor.collect[List](1)

    futureTweetsList.map { tweets =>
      Ok(Json.toJson(tweets))
    }
  }

  def getByWord(word: String, limit: Int) = Action.async {
    val cursor: Cursor[Tweet] = collection.find(Json.obj(
      "text" -> Json.obj(
        "$regex" -> (".*" + word + ".*")
      )
    )).sort(Json.obj("_id" -> -1)).cursor[Tweet]

    val futureTweetsList: Future[List[Tweet]] = cursor.collect[List](limit)

    futureTweetsList.map { tweets =>
      Ok(Json.toJson(tweets))
    }
  }
}