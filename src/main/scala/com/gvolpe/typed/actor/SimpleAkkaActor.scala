package com.gvolpe.typed.actor

import akka.actor.{Actor, ActorSystem, Props}
import com.gvolpe.typed.actor.SimpleAkkaActor.{SimpleMessageOne, SimpleMessageTwo}

object SimpleAkkaActor {
  sealed trait SimpleMessage
  case class SimpleMessageOne(v: String) extends SimpleMessage
  case class SimpleMessageTwo(v: String) extends SimpleMessage

  def props(implicit s: ActorSystem) = s.actorOf(Props[SimpleAkkaActor])
}

class SimpleAkkaActor extends Actor {
  override def receive: Receive = {
    case one: SimpleMessageOne => println(s"received: $one")
    case two: SimpleMessageTwo => println(s"received: $two")
    case _                     => println("Message not recognized!")
  }
}
