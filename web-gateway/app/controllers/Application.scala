package controllers

import javax.inject._
import play.api.mvc._
import org.example.hello.api.HelloService

class Application @Inject() (cc: ControllerComponents, helloService: HelloService) extends AbstractController(cc) {

  def index() = Action { implicit request ⇒
    Ok("Got request [" + request + "]")
  }

  def hello(id: String) = Action { implicit request ⇒
    Ok("Got request [" + request + "]")
  }
}
