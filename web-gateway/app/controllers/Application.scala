package controllers

import javax.inject._
import play.api.mvc._

class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request =>
    Ok("Got request [" + request + "]")
  }
}
