package com.gvolpe.typed.actor

import akka.actor.ActorSystem
import com.gvolpe.typed.actor.StrictTypedActor._
import de.knutwalker.akka.typed._

object StrictTypedActor {
  sealed trait StrictMessage
  case object StrictOne extends StrictMessage
  case object StrictTwo extends StrictMessage

  case object NotStrict

  def props(implicit s: ActorSystem) = ActorOf(Props[StrictMessage, StrictTypedActor], name = "StrictTypedActor")
}

class StrictTypedActor extends TypedActor.Of[StrictMessage] {
  override def typedReceive: TypedReceive = {
    case StrictOne => println(s"received StrictOne at $typedSelf")
    case StrictTwo => println(s"received StrictTwo at $typedSelf")
//    case NotStrict => println(s"this does not compile!")
  }
}
