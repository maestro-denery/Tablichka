package io.denery

import org.junit.jupiter.api.Test
import cats.effect.{ContextShift, ExitCode, Fiber, IO}

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Test class where you (developer) can experiment with all scala (or non scala) things!
 */
class Experimentarium:

  given ContextShift[IO] = IO.contextShift(global)

  extension [A] (io: IO[A])
    def debug: IO[A] = io.map { value =>
      println(s"[${Thread.currentThread().getName}] $value")
      value
    }

  @Test
  def fibers(): Unit = {
    val a: IO[Int] = IO(152)
    val b: IO[String] = IO("aboba")

    diffThreads(a, b).unsafeRunSync()

  }

  def diffThreads[A, B](io: IO[A], io1: IO[B]) = for {
    fib <- io.debug.start
    _ <- io1.debug
    aftermath <- fib.join
  } yield ()
