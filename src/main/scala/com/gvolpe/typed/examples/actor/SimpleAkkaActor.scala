package com.gvolpe.typed.examples.actor

import akka.actor.{Actor, Props}
import com.gvolpe.typed.examples.actor.SimpleAkkaActor.{SimpleMessageOne, SimpleMessageTwo}

object SimpleAkkaActor {
  sealed trait SimpleMessage extends Product with Serializable
  final case class SimpleMessageOne(v: String) extends SimpleMessage
  final case class SimpleMessageTwo(v: String) extends SimpleMessage

  def props = Props[SimpleAkkaActor]
}

class SimpleAkkaActor extends Actor {
  override def receive: Receive = {
    case one: SimpleMessageOne => println(s"received: $one")
    case two: SimpleMessageTwo => println(s"received: $two")
    case _                     => println("Message not recognized!")
  }
}
