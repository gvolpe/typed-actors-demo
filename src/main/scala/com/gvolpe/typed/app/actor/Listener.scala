package com.gvolpe.typed.app.actor

import com.gvolpe.typed.app.messages.PiApproximation
import de.knutwalker.akka.typed._

object Listener {
  def props = PropsFor[Listener]
}

class Listener extends TypedActor.Of[PiApproximation] {

  override def typedReceive: TypedReceive = {
    case PiApproximation(pi, duration) =>
      println("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s ms"
        .format(pi, duration))
      context.system.terminate()
  }

}