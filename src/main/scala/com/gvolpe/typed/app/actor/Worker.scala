package com.gvolpe.typed.app.actor

import com.gvolpe.typed.app.messages.{Result, Work}
import de.knutwalker.akka.typed._

object Worker {
  def props = PropsFor[Worker]
}

class Worker extends TypedActor.Of[Work] {

  private def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0
    for (i ← start until (start + nrOfElements))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    acc
  }

  override def typedReceive: TypedReceive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiFor(start, nrOfElements))
  }

}