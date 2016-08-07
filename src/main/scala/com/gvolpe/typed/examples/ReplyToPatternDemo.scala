package com.gvolpe.typed.examples

import akka.actor.ActorSystem
import com.gvolpe.typed.examples.actor.ReplyToPatternActor
import de.knutwalker.akka.typed.{Typed, _}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object ReplyToPatternDemo extends App {

  implicit val system = ActorSystem("typed-actors-demo")

  case class MyMessage(payload: String)(val replyTo: ActorRef[MyResponse])
  case class MyResponse(payload: String)

  import akka.actor.ActorDSL._
  val box = inbox()

  val replyToPatternActor: ActorRef[MyMessage] = Typed[ReplyToPatternActor].create()
  box.send(replyToPatternActor.untyped, MyMessage("ReplyTo Pattern!")(box.receiver.typed))

  val MyResponse(response) = box.receive(1.second) // Now it works!
  println(response)

  system.scheduler.scheduleOnce(2.seconds)(system.terminate())

}
