package com.gvolpe.typed

import akka.actor._
import com.gvolpe.typed.actor.SimpleAkkaActor.{SimpleMessage, SimpleMessageOne, SimpleMessageTwo}
import de.knutwalker.akka.typed.{ActorRef, _}
import com.gvolpe.typed.actor._
import com.gvolpe.typed.actor.SimpleTypedActor._
import com.gvolpe.typed.actor.StrictTypedActor.StrictOne
import com.gvolpe.typed.actor.UnionTypedActor._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Demo extends App {

  implicit val system = ActorSystem("typed-actors-demo")

  case object SomeOtherMessage

  val actor = SimpleTypedActor.props

  actor ! Foo("Hey you be strict!")
  actor ! Bar("What's the craic?")

  // You'll get a compilation error!
  // actor ! SomeOtherMessage

  val unionActor = UnionTypedActor.props

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

  val simpleAkkaActor: UntypedActorRef = SimpleAkkaActor.props

  simpleAkkaActor ! SimpleMessageOne("Hey!")
  simpleAkkaActor ! "Whatever, it's untyped!"

  val simpleAkkaActorTyped: ActorRef[SimpleMessage] = simpleAkkaActor.typed[SimpleMessage]

  simpleAkkaActorTyped ! SimpleMessageTwo("Hey!")

  // You'll get a compilation error!
  // simpleAkkaActorTyped ! "Now it will fail!"

  val strictTypedActor = StrictTypedActor.props

  strictTypedActor ! StrictOne

  // You'll get a compilation error!
  // strictTypedActor ! "Whatever"

  val strictUnionTypedActor = StrictUnionTypedActor.props

  strictUnionTypedActor ! StrictUnionTypedActor.Foo()

  // You'll get a compilation error!
  // strictUnionTypedActor ! "Whatever"

  system.scheduler.scheduleOnce(2.seconds)(system.terminate())

}
