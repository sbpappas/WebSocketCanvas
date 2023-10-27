package actors

import akka.actor.Actor
import akka.actor.ActorRef
import MovingActor.SendCoordinates
import akka.actor.Props


class moveManager extends Actor {
    private var movers = Map.empty[String, ActorRef]
    import moveManager._

    def receive = {
        case NewMover(mover, userId) => 
            broadCastCoordinates(userId, "200:200") //what should i put here instead?
            println("new mover")
        case Coordinates(userId, x, y) => 
            broadCastCoordinates(userId, s"$x:$y")
            println("broadcast")
        case m => println("Unhandled message in Move Manager: " + m)
    }
    
    def broadCastCoordinates(senderUserId: String, coor: String): Unit = {
        println("in broadcastcoor")
        movers.foreach { 
            case (userId, mover) if userId != senderUserId =>
                println("broadcast coordinates to all")
                mover ! SendCoordinates
                //mover ! movingActor.SendCoordinates
            case m => println("unhandled broadcast error")
        }
    }
}

object moveManager{
    case class NewMover(mover: ActorRef, userId: String)
    case class Coordinates(userId: String, x: Double, y: Double)
}