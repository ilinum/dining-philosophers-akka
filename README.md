# dining-philosophers-akka
This is an attempt to solve the [Dining Philosophers Problem](https://en.wikipedia.org/wiki/Dining_philosophers_problem) with [Akka](http://akka.io)
## Dining Philosophers Problem
Dining philosophers problem was initially introduced by Edsger Dijkstra, as an excercise to illustrate synchronization issues and techniques for resolving them.

#### Problem Statement
N philosophers are sitting at a table with N chopsticks, each needs two chopsticks to eat, and each philosopher alternates between thinking and eating.

![dining-philosophers](https://cloud.githubusercontent.com/assets/5924452/11609692/bf6e310c-9b9e-11e5-97a7-972e094178ae.png)

Picture and problem statement taken from [Alison Norman](http://www.cs.utexas.edu/~ans)'s slides for Principles of Computer Systems class

#### Solution
Multiple solutions to this problem exist.
This is an implementation of an arbitrator solution, where the waiter controlls all the chopsticks and gives them out to when they ask philosophers.

##Akka
Akka is a toolkit for concurrent, resilient and distributed applications that emphasizes [actor-based concurrency](https://en.wikipedia.org/wiki/Actor_model)

