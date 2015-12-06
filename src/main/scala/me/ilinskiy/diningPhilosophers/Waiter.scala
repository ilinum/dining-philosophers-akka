package me.ilinskiy.diningPhilosophers

import akka.actor.{Actor, ActorRef}
import me.ilinskiy.diningPhilosophers.Philosopher._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by ilinum on 06.12.15.
  */
class Waiter extends Actor {
  case class TablePhilosopher(name: String, var isEating: Boolean, actorRef: ActorRef)
  var philosophers = new ArrayBuffer[TablePhilosopher]()

  override def receive: Receive = {
    case Introduce(name) =>
      println(s"$name introduced themselves!")
      philosophers += TablePhilosopher(name, isEating = false, sender())
    case Hungry =>
      val phil = findPhilosopher(sender())
      val i = philosophers.indexOf(phil)
      val leftNeighbor =
        if (i == 0) philosophers.last
        else philosophers(i - 1)
      val rightNeighbor = philosophers((i + 1) % philosophers.size)
      if (!leftNeighbor.isEating && !rightNeighbor.isEating) {
        phil.isEating = true
        sender() ! Chopsticks
      } else {
        sender() ! NoChopsticks
      }
    case FinishedEating => findPhilosopher(sender()).isEating = false
  }

  private def findPhilosopher(actorRef: ActorRef): TablePhilosopher = philosophers.find(_.actorRef == sender()) match {
    case Some(phil) => phil
    case _ => throw new IllegalArgumentException("Need to introduce yourself first!")
  }
}
