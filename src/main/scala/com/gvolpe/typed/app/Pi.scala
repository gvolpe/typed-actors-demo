package com.gvolpe.typed.app

import akka.actor.ActorSystem
import com.gvolpe.typed.app.actor.{Listener, Master}
import com.gvolpe.typed.app.messages.Calculate
import de.knutwalker.akka.typed._

object Pi extends App {

  calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int): Unit = {

    implicit val system = ActorSystem("PiSystem")

    val listener = ActorOf(Listener.props, name = "listener")

    val master = ActorOf(Master.props(nrOfWorkers, nrOfElements, nrOfMessages, listener), name = "master")

    master ! Calculate

  }

}