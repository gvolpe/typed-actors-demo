package com.gvolpe.typed.examples

import akka.actor.ActorSystem
import com.gvolpe.typed.examples.actor.SimpleAkkaActor.{SimpleMessage, SimpleMessageOne, SimpleMessageTwo}
import com.gvolpe.typed.examples.actor.SimpleTypedActor.{Bar, Foo}
import com.gvolpe.typed.examples.actor.StrictTypedActor.StrictOne
import com.gvolpe.typed.examples.actor.UnionTypedActor.{SampleOne, SampleThree, SampleTwo}
import com.gvolpe.typed.examples.actor._
import de.knutwalker.akka.typed._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Demo extends App {

  implicit val system = ActorSystem("typed-actors-demo")

  case object SomeOtherMessage

  val actor = ActorOf(SimpleTypedActor.props, name = "simple-typed-actor")

  actor ! Foo("Hey you be strict!")
  actor ! Bar("What's the craic?")

  // You'll get a compilation error!
  // actor ! SomeOtherMessage

  val unionActor = ActorOf(UnionTypedActor.props)

  unionActor ! SampleOne(5)
  unionActor ! SampleTwo("Hey!")
  unionActor ! SampleThree(false)

  // You'll get a compilation error!
  // unionActor ! SomeOtherMessage

  val untypedActor: UntypedActorRef = unionActor.untyped

  val sampleTwoOnlyActor = unionActor.only[SampleTwo]

  sampleTwoOnlyActor ! SampleTwo("Hello!")

  // You'll get a compilation error!
  // sampleTwoOnlyActor ! SampleOne(1)

  val simpleAkkaActor: UntypedActorRef = system.actorOf(SimpleAkkaActor.props)

  simpleAkkaActor ! SimpleMessageOne("Hey!")
  simpleAkkaActor ! "Whatever, it's untyped!"

  val simpleAkkaActorTyped: ActorRef[SimpleMessage] = simpleAkkaActor.typed[SimpleMessage]

  simpleAkkaActorTyped ! SimpleMessageTwo("Hey!")

  // You'll get a compilation error!
  // simpleAkkaActorTyped ! "Now it will fail!"

  val strictTypedActor = ActorOf(StrictTypedActor.props, name = "StrictTypedActor")

  strictTypedActor ! StrictOne

  // You'll get a compilation error!
  // strictTypedActor ! "Whatever"

  val strictUnionTypedActor = ActorOf(StrictUnionTypedActor.props)

  strictUnionTypedActor ! StrictUnionTypedActor.Foo()

  // You'll get a compilation error!
  // strictUnionTypedActor ! "Whatever"

  system.scheduler.scheduleOnce(2.seconds)(system.terminate())

}
