package actors

import akka.actor.Actor
import akka.actor.ActorRef
import MovingActor.SendCoordinates
import akka.actor.Props
import scala.collection.mutable.Map


class moveManager extends Actor {
    private var movers = Map.empty[String, ActorRef] //[userid, mover, coor]
    private var locations = Map.empty[ActorRef, String]

    import moveManager._

    def receive = {
        case NewMover(mover, userId, loc, out) =>
            //movers += (userId -> mover, "0.0:0.0")
            locations += (out -> loc)
            movers+=(userId -> mover)
            // Initialize the user's position.
            mover ! SendCoordinates(loc)
            println("new mover")
        case Coordinates(userId, x, y, userRef) =>
            //locations(userRef) = s"$x:$y" //update the location of the image that just moved
            locations(userRef) = s"$x:$y"
            broadCastCoordinates(userId, s"$x:$y")
            //println("broadcasted")
        case SendCoordinates(coor) =>
            println("error: in manager sendcoordinates") //dont use this
            // Ignore the SendCoordinates message, as it's not needed here.
        case m => println("Unhandled message in Move Manager: " + m)
    }

    def broadCastCoordinates(senderUserId: String, coor: String): Unit = {
        println("broadcast " + s"$coor")
        val concatenatedString: String = locations.values.mkString(" ")
        println(concatenatedString)
        for ((userId, mover) <- movers) {
            println("broadcast coordinates to all")
            //for ((userId, mover) <- movers) {
                mover ! SendCoordinates(concatenatedString)
            //}
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
    case class NewMover(mover: ActorRef, userId: String, loc: String, out: ActorRef)
    case class Coordinates(userId: String, x: Double, y: Double, userRef: ActorRef)
}