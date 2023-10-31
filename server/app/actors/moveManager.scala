package actors

import akka.actor.Actor
import akka.actor.ActorRef
import MovingActor.SendCoordinates
import akka.actor.Props


class moveManager extends Actor {
    private var movers = Map.empty[String, ActorRef] //[userid, mover, coor]

    import moveManager._

    def receive = {
        case NewMover(mover, userId) =>
            //movers += (userId -> mover, "0.0:0.0")
            movers+=(userId -> mover)
            // Initialize the user's position.
            mover ! SendCoordinates("0.0:0.0")
            println("new mover")
        case Coordinates(userId, x, y) =>
            
            broadCastCoordinates(userId, s"$x:$y")
            println("broadcasted")
        case SendCoordinates(coor) =>
            println("error: in manager sendcoordinates")
            // Ignore the SendCoordinates message, as it's not needed here.
        case m => println("Unhandled message in Move Manager: " + m)
    }

    def broadCastCoordinates(senderUserId: String, coor: String): Unit = {
        println("broadcast " + s"$coor")
        //for {(userId, mover) <- movers if userId != senderUserId} {
        for ((userId, mover) <- movers) {
            println("broadcast coordinates to all")
            mover ! SendCoordinates(coor)
        }
    }
}

/*
    
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
}*/

object moveManager{
    case class NewMover(mover: ActorRef, userId: String)
    case class Coordinates(userId: String, x: Double, y: Double)
}