package actors 

import akka.actor.Actor 
import akka.actor.Props
import akka.actor.ActorRef
import MovingActor._

class MovingActor(out: ActorRef, manager: ActorRef) extends Actor {
    manager ! moveManager.NewMover(self)
    

    def receive = {
        case s: String => manager ! moveManager.Coordinates(s)
            println("got message: " + s)
        case SendCoordinates(coor) => out ! coor
        case m => println("Unhandled message in MovingActor: " + m)
    }
} 

object MovingActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new MovingActor(out, manager)) 

    case class SendCoordinates(coor: String)
}