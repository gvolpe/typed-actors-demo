package com.gvolpe.typed.app.actor

import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import com.gvolpe.typed.app.messages._
import de.knutwalker.akka.typed._

object Master {
  def props(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int, listener: ActorRef[PiApproximation]) =
    PropsFor(new Master(nrOfWorkers, nrOfElements, nrOfMessages, listener))
}

class Master(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int, listener: ActorRef[PiApproximation]) extends TypedActor.Of[PiMessage] {

  var pi: Double = _
  var nrOfResults: Int = _
  val start: Long = System.currentTimeMillis

  val workerRouter: Router = {
    val routees = Vector.fill(5) {
      val r = ActorOf(Worker.props).untyped
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  override def typedReceive: TypedReceive = {
    case Calculate =>
      for (i ← 0 until nrOfMessages) workerRouter.route(Work(i * nrOfElements, nrOfElements), self)
    case Result(value) =>
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, duration = System.currentTimeMillis - start)
        // Stops this actor and all its supervised children
        context.stop(self)
      }
  }

}