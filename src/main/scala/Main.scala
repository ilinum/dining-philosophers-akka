import Philosopher._
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.collection.mutable.ArrayBuffer

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

object Main extends App {
  val system = ActorSystem("Dinner")
  val waiter = system.actorOf(Props[Waiter])

  system.actorOf(Props(new Philosopher(waiter, "A")))
  system.actorOf(Props(new Philosopher(waiter, "B")))
  system.actorOf(Props(new Philosopher(waiter, "C")))
  system.actorOf(Props(new Philosopher(waiter, "D")))
  system.actorOf(Props(new Philosopher(waiter, "E")))
}
