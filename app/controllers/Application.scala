// package controllers

// import play.api._
// import play.api.libs.iteratee.Iteratee
// import play.api.libs.json.{JsArray, Json}
// import play.api.mvc._
// import play.api.libs.concurrent.Execution.Implicits.defaultContext
// import play.modules.reactivemongo.MongoController
// import play.modules.reactivemongo.json.collection.JSONCollection
// import reactivemongo.api.Cursor

// import scala.concurrent.Future

// object Application extends Controller with MongoController{

//   def collection: JSONCollection = db.collection[JSONCollection]("word")

//   import models._
//   // import models.JsonFormats._

//   def index = Action.async {
//     // let's do our query
//     val cursor: Cursor[Word] = collection.find(Json.obj()).cursor[Word]

//     // gather all the JsObjects in a list
//     val futureWordsList: Future[List[Word]] = cursor.collect[List]()

//     val futureWordsJsonArray: Future[JsArray] = futureWordsList.map { words =>
//       Json.arr(words)
//     }

//     // everything's ok! Let's reply with the array
//     futureWordsJsonArray.map { words=>
//       Ok(words)
//     }
//   }

//   def store = Action.async(parse.json) { request =>
    
//      * request.body is a JsValue.
//      * There is an implicit Writes that turns this JsValue as a JsObject,
//      * so you can call insert() with this JsValue.
//      * (insert() takes a JsObject as parameter, or anything that can be
//      * turned into a JsObject using a Writes.)
     
//     request.body.validate[Word].map { word =>
//       // `word` is an instance of the case class `models.Word`
//       collection.insert(word).map { lastError =>
//         Logger.debug(s"Successfully inserted with LastError: $lastError")
//         Ok("")
//       }
//     }.getOrElse(Future.successful(BadRequest("invalid json")))
//   }

//   def delete(value: String) = Action.async {
//     // let's do our query
//     val cursor: Cursor[Word] = collection.
//       // find all people with name `name`
//       find(Json.obj("value" -> value)).
//       // sort them by creation date
//       // sort(Json.obj("created" -> -1)).
//       // perform the query and get a cursor of JsObject
//       cursor[Word]

//     // gather all the JsObjects in a list
//     val futureWordsList: Future[List[Word]] = cursor.collect[List]()

//     val futureWordsJsonArray: Future[JsArray] = futureWordsList.map { words =>
//       Json.arr(words)
//     }

//     // everything's ok! Let's reply with the array
//     futureWordsJsonArray.map { words=>
//       Ok(words)
//     }
//   }

//   def options(path: String) = Action {
//     Ok("")
//   }

// }