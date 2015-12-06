package me.ilinskiy.diningPhilosophers

import akka.actor.{ActorSystem, Props}

object Main extends App {
  val system = ActorSystem("Dinner")
  val waiter = system.actorOf(Props[Waiter])

  system.actorOf(Props(new Philosopher(waiter, "A")))
  system.actorOf(Props(new Philosopher(waiter, "B")))
  system.actorOf(Props(new Philosopher(waiter, "C")))
  system.actorOf(Props(new Philosopher(waiter, "D")))
  system.actorOf(Props(new Philosopher(waiter, "E")))
}
