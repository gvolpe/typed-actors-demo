package com.gvolpe.typed.actor

import akka.actor.ActorSystem
import com.gvolpe.typed.actor.StrictUnionTypedActor._
import de.knutwalker.akka.typed._

object StrictUnionTypedActor {
  case class Foo()
  case class Bar()
  case class Baz()

  // Message type derivation using PropsFor (only for typed actor)
  def props(implicit s: ActorSystem) = ActorOf(PropsFor[StrictUnionTypedActor]).or[Bar].or[Baz]
}

// NOTE: It doesn't allow case objects as types
class StrictUnionTypedActor extends TypedActor.Of[Foo | Bar | Baz] {
  override def typedReceive: TypedReceive = TotalUnion
      .on[Foo]{case f:Foo => println(s"received $f")}
      .on[Bar]{case b:Bar => println(s"received $b")}
      .on[Baz]{case b:Baz => println(s"received $b")}
      .apply

// NOTE: This won't compile because it fails on type-check exhaustiveness
//  override def typedReceive: TypedReceive = TotalUnion
//    .on[Foo]{case f:Foo => println(s"received $f")}
//    .on[Baz]{case b:Baz => println(s"received $b")}
//    .apply
}