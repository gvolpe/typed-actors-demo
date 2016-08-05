package com.gvolpe.typed.actor

import akka.actor.{Actor, ActorSystem}
import de.knutwalker.akka.typed._
import com.gvolpe.typed.actor.UnionTypedActor._

object UnionTypedActor {
  case class SampleOne(n: Int)
  case class SampleTwo(v: String)
  case class SampleThree(b: Boolean)

  def props(implicit s: ActorSystem) = ActorOf(Props[SampleOne, UnionTypedActor]).or[SampleTwo].or[SampleThree]
}

class UnionTypedActor extends Actor {
  override def receive: Receive = {
    case one: SampleOne     => println(s"received: $one")
    case two: SampleTwo     => println(s"received: $two")
    case three: SampleThree => println(s"received: $three")
  }
}
