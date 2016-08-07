package com.gvolpe.typed.examples.actor

import akka.actor.Actor
import de.knutwalker.akka.typed._
import com.gvolpe.typed.examples.actor.UnionTypedActor._

object UnionTypedActor {
  case class SampleOne(n: Int)
  case class SampleTwo(v: String)
  case class SampleThree(b: Boolean)

  def props = Props[SampleOne, UnionTypedActor].or[SampleTwo].or[SampleThree]
}

class UnionTypedActor extends Actor {
  override def receive: Receive = {
    case one: SampleOne     => println(s"received: $one")
    case two: SampleTwo     => println(s"received: $two")
    case three: SampleThree => println(s"received: $three")
  }
}
