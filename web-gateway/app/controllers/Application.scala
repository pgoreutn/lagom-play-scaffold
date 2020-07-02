package controllers

import javax.inject._
import org.example.hello.api
import play.api.mvc._
import org.example.hello.api.HelloService
import play.api.libs.json.{ JsError, Json }
import utils.MyExecutionContext

import scala.concurrent.Future

case class GreetingMessage(message: String)

object GreetingMessage {
  implicit val format = Json.format[GreetingMessage]
}

class Application @Inject() (cc: ControllerComponents, helloService: HelloService, myExecutionContext: MyExecutionContext) extends AbstractController(cc) {

  def index() = Action { implicit request ⇒
    Ok("Got request [" + request + "]")
  }

  def hello(id: String) = Action.async { implicit request ⇒
    helloService.hello(id).invoke().map { str ⇒
      Ok(str)
    }(myExecutionContext)
  }

  def useGreeting(id: String) = Action(parse.json).async { implicit request ⇒
    val format = request.body.validate[GreetingMessage]

    format.fold(
      errors ⇒ {
        Future.successful(BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))))
      },
      format ⇒ {
        helloService.useGreeting(id).invoke(api.GreetingMessage(format.message)).map { _ ⇒
          Ok
        }(myExecutionContext)
      }
    )
  }
}
