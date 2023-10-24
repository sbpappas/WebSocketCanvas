package actors

import akka.actor.Actor
import akka.actor.ActorRef


class moveManager extends Actor {
    private var movers = List.empty[ActorRef]
    import moveManager._
    def receive = {
        case NewMover(mover) => movers ::= mover
        case Coordinates(coor) => 
        case m => println("Unhandled message in Move Manager: " + m)
    }
}

object moveManager{
    case class NewMover(mover: ActorRef)
    case class Coordinates(coor: String)
}