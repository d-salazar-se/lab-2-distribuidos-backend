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

object WordController extends Controller with MongoController{

  def collection: JSONCollection = db.collection[JSONCollection]("words")

  import models._
  import models.JsonWordFormats._

  def index = Action.async {
    val cursor: Cursor[Word] = collection.find(Json.obj()).cursor[Word]

    val futureWordsList: Future[List[Word]] = cursor.collect[List]()

    futureWordsList.map { words =>
      Ok(Json.toJson(words))
    }
  }

  def store = Action.async(parse.json) { request =>
    request.body.validate[Word].map { word =>
      collection.insert(word).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Ok("")
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def delete = Action.async(parse.json) { request =>
      request.body.validate[Word].map { word =>
        collection.remove(Json.obj("value" -> word.value)).map { lastError =>
          Logger.debug(s"Successfully removed with LastError: $lastError")
          Ok("")
        }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
    }
}