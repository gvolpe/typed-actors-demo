package com.gvolpe.typed

import akka.actor.ActorSystem
import com.gvolpe.typed.actor.{BrokenReplyToPatternActor, ReplyToPatternActor}
import de.knutwalker.akka.typed._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ReplyToPatternDemo extends App {

  implicit val system = ActorSystem("typed-actors-demo")

  case class MyMessage(payload: String)
  case class MyResponse(payload: String)

  import akka.actor.ActorDSL._
  val box = inbox()

  val brokenReplyToPatternActor: ActorRef[MyMessage] = Typed[BrokenReplyToPatternActor].create()
  box.send(brokenReplyToPatternActor.untyped, MyMessage("ReplyTo Pattern!"))

  val result: Any = box.receive(1.second)
  // val MyResponse(result) = box.receive(1.second) // This will throw a MatchError in Runtime!!!

  val replyToPatternActor: ActorRef[MyMessage] = Typed[ReplyToPatternActor].create()
  box.send(replyToPatternActor.untyped, MyMessage("ReplyTo Pattern!"))

  val MyResponse(response) = box.receive(1.second) // Now it works!
  println(response)

  system.scheduler.scheduleOnce(2.seconds)(system.terminate())

}
