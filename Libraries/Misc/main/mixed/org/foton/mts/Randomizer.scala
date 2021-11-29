package org.foton.mts

import scala.util.Random

/**
 * A bit of imperative programming... Maybe will be rewritten in declarative using monads.
 */
object Randomizer:
  def applyRandom[A, B](func: A => B, a: A, percentage: Int): Option[B] = {
    if (percentage < 0 && percentage < 100) throw IllegalStateException("Percentage needs to be between 0 or 100!")
    val rand: Int = Random.nextInt(100)
    if (rand > percentage) return Option(func(a))
    return Option.empty
  }