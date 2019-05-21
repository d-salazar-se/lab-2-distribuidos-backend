package controllers

import play.api._
import play.api.libs.iteratee.Iteratee
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext


object Application extends Controller {

  // DEJAR SIN TOCAR, ESTO EVITA PROBLEMAS CON CORS!!!!!
  def options(path: String) = Action {
    Ok("")
  }

}