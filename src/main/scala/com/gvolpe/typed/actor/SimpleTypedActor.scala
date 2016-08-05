package com.gvolpe.typed.actor

import akka.actor.{Actor, ActorSystem}
import de.knutwalker.akka.typed._
import com.gvolpe.typed.actor.SimpleTypedActor._

object SimpleTypedActor {
  sealed trait MyMessage
  case class Foo(foo: String) extends MyMessage
  case class Bar(bar: String) extends MyMessage

  def props(implicit s: ActorSystem) = ActorOf(Props[MyMessage, SimpleTypedActor], name = "typed-actor")
}

class SimpleTypedActor extends Actor {
  override def receive: Receive = {
    case Foo(foo) => println(s"received a Foo: $foo")
    case Bar(bar) => println(s"received a Bar: $bar")
  }
}
