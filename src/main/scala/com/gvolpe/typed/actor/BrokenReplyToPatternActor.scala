package com.gvolpe.typed.actor

import com.gvolpe.typed.ReplyToPatternDemo.MyMessage
import de.knutwalker.akka.typed._

case class BrokenReplyToPatternActor() extends TypedActor.Of[MyMessage] {
  override def typedReceive: TypedReceive = {
    case m: MyMessage => sender() ! m.payload
  }
}
