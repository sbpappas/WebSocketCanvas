package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import java.lang.ProcessBuilder.Redirect
import play.api.libs.json._
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.libs.streams.ActorFlow

@Singleton
class WebController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

  def draw = Action { implicit request =>
    Ok(views.html.draw())
  }
  def getSocket = WebSocket.accept[String, String]{ request =>//not strings
    println("getting socket")
    ActorFlow.actorRef {out=>
        ???    
    }  
  }
}