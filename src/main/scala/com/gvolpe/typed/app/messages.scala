package com.gvolpe.typed.app

object messages {

  sealed trait PiMessage extends Product with Serializable
  case object Calculate extends PiMessage
  final case class Result(value: Double) extends PiMessage

  final case class Work(start: Int, nrOfElements: Int)

  final case class PiApproximation(pi: Double, duration: Long)

}
