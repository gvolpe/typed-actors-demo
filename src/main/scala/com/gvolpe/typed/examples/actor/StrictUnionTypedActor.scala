package com.gvolpe.typed.examples.actor

import com.gvolpe.typed.examples.actor.StrictUnionTypedActor._
import de.knutwalker.akka.typed._

object StrictUnionTypedActor {
  case class Foo()
  case class Bar()
  case class Baz()

  // Message type derivation using PropsFor (only for typed actor)
  def props = PropsFor[StrictUnionTypedActor]
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