package com.gvolpe.typed.actor

import com.gvolpe.typed.ReplyToPatternDemo.{MyMessage, MyResponse}
import de.knutwalker.akka.typed._

case class ReplyToPatternActor() extends TypedActor.Of[MyMessage] {
  override def typedReceive: TypedReceive = {
    case m@MyMessage(payload) => m.replyTo ! MyResponse(payload)
    //    case m: MyMessage => m.replyTo ! m.payload // This does not compile!
  }
}
