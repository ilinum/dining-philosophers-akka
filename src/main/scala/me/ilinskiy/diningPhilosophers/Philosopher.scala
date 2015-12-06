package me.ilinskiy.diningPhilosophers

import akka.actor.{Actor, ActorRef}
import me.ilinskiy.diningPhilosophers.Philosopher._

/**
  * @author Svyatoslav Ilinskiy
  * @since 05.12.2015
  */
class Philosopher(waiter: ActorRef, name: String) extends Actor {

  waiter ! Introduce(name)
  think()

  def eat(): Unit = {
    Thread.sleep(eatTime)
    println(s"mmm, this was delicious! - Philosopher $name")
  }

  def think(): Unit = {
    Thread.sleep(thinkTime)
    println(s"I'm hungry now! - Philosopher $name")
    waiter ! Hungry
  }

  override def receive: Receive = {
    case Chopsticks =>
      eat()
      sender() ! FinishedEating
      think()
    case NoChopsticks =>
      think()
  }
}

object Philosopher {
  case object Chopsticks
  case object NoChopsticks
  case object Hungry
  case object FinishedEating
  case class Introduce(name: String)

  val eatTime = 2000L
  val thinkTime = 4000L
}
