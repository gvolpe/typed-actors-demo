package com.gvolpe.typed.examples.actor

import akka.actor.Actor
import de.knutwalker.akka.typed._
import com.gvolpe.typed.examples.actor.SimpleTypedActor._

object SimpleTypedActor {
  sealed trait MyMessage extends Product with Serializable
  final case class Foo(foo: String) extends MyMessage
  final case class Bar(bar: String) extends MyMessage

  def props = Props[MyMessage, SimpleTypedActor]
}

class SimpleTypedActor extends Actor {
  override def receive: Receive = {
    case Foo(foo) => println(s"received a Foo: $foo")
    case Bar(bar) => println(s"received a Bar: $bar")
  }
}
