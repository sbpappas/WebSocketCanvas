package actors

import akka.actor.Actor
import akka.actor.ActorRef
import MovingActor.SendCoordinates
import akka.actor.Props


class moveManager extends Actor {
    private var movers = List.empty[ActorRef]
    import moveManager._

    def receive = {
        case NewMover(mover) => movers ::= mover
        case Coordinates(coor) => broadCastCoordinates(coor)
        case m => println("Unhandled message in Move Manager: " + m)
    }
    
    def broadCastCoordinates(coor: String): Unit = {
        movers.foreach { mover =>
            mover ! SendCoordinates(coor)
        }
    }
}

object moveManager{
    case class NewMover(mover: ActorRef)
    case class Coordinates(coor: String)
}