package actors 

import akka.actor.Actor 
import akka.actor.Props
import akka.actor.ActorRef
import MovingActor._
import java.net.CookieHandler 
import play.api.libs.json._

class MovingActor(out: ActorRef, manager: ActorRef, userId: String) extends Actor {
    private var userX: Double = 0
    private var userY: Double = 0
    manager ! moveManager.NewMover(self, userId, s"$userX:$userY", out)


    def receive = {
        case s: String if s.contains(":") =>
            val coors = s.split(":")
            if (coors.length == 2) {
                val x = coors(0).toDouble
                val y = coors(1).toDouble        
                    userX = x
                    userY = y
                manager ! moveManager.Coordinates(userId, userX, userY, out)
                println("got message: " + x + ", " + y)
            }
        case SendCoordinates(coor) =>
            out ! coor
        case m => println("Unhandled message in MovingActor: " + m)
    }
}


object MovingActor {
    def props(out: ActorRef, manager: ActorRef, userId: String) = Props(new MovingActor(out, manager, userId)) 

    case class Coordinates(x: Double, y: Double)
    case class SendCoordinates(coor: String)
    //case object SendCoordinates

}