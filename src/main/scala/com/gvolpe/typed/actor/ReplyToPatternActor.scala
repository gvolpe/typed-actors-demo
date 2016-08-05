package com.gvolpe.typed.actor

import com.gvolpe.typed.ReplyToPatternDemo.{MyMessage, MyResponse}
import de.knutwalker.akka.typed._

case class ReplyToPatternActor() extends TypedActor.Of[MyMessage] {
  override def typedReceive: TypedReceive = {
    case m@MyMessage(payload) => sender() ! MyResponse(payload)
  }
}
