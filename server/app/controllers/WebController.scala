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
import actors.MovingActor
import actors.moveManager
import akka.actor.Props
import java.util.UUID

@Singleton
class WebController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

  val manager = system.actorOf(Props[moveManager], "Manager")
  def draw = Action { implicit request =>
    Ok(views.html.draw())
  }
  def getSocket = WebSocket.accept[String, String]{ request =>
    val userId = UUID.randomUUID().toString
    ActorFlow.actorRef { out=>
        MovingActor.props(out, manager, userId)    
    }  
  }
}