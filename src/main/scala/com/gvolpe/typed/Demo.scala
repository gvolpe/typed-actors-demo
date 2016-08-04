package com.gvolpe.typed

import akka.actor._
import de.knutwalker.akka.typed._
import com.gvolpe.typed.actor.TypedActorExample
import com.gvolpe.typed.actor.TypedActorExample.{Bar, Foo}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Demo extends App {

  implicit val system = ActorSystem("typed-actors-demo")

  case object SomeOtherMessage

  val actor = TypedActorExample.props

  actor ! Foo("Hey you!")
  actor ! Bar("What's the craic?")

  // You'll get a compilation error!
  //actor ! SomeOtherMessage

  system.scheduler.scheduleOnce(2.seconds)(system.terminate())

}
