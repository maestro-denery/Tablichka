package io.denery

import org.junit.Test
import cats.effect.{ContextShift, Fiber, IO}

import scala.concurrent.ExecutionContext.Implicits.global

class FibersTest:

  given ContextShift[IO] = IO.contextShift(global)

  @Test
  def check(): Unit = {
    val a: IO[Unit] = IO.raiseError(new Exception("kaboom!"))
    val b = IO(println("WTF!?"))
    for {
      fiber <- a.start
      _ <- b.handleErrorWith(error => {
        fiber.cancel *> IO.raiseError(error)
      })
      aftermath <- fiber.join
    } yield aftermath
  }
