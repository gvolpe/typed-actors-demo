package com.gvolpe.typed.examples.actor

import com.gvolpe.typed.examples.actor.StrictTypedActor._
import de.knutwalker.akka.typed._

object StrictTypedActor {
  sealed trait StrictMessage
  case object StrictOne extends StrictMessage
  case object StrictTwo extends StrictMessage

  case object NotStrict

  def props = Props[StrictMessage, StrictTypedActor]
}

class StrictTypedActor extends TypedActor.Of[StrictMessage] {
  override def typedReceive: TypedReceive = {
    case StrictOne => println(s"received StrictOne at $typedSelf")
    case StrictTwo => println(s"received StrictTwo at $typedSelf")
//    case NotStrict => println(s"this does not compile!")
  }
}
