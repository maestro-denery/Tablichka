package io.denery

import cats.effect.{IO, LiftIO}
import org.junit.jupiter.api.Test

import java.io.{BufferedReader, File, InputStream, InputStreamReader}
import java.nio.charset.StandardCharsets

/**
 * Algebraic Data Type (ADT) LiftIO example, PLEASE DO NOT USE this ADT example in REAL plugin code,
 * it is just Denery's researching in FP with Cats / Tofu libraries, use instead Tagless Final version of Example if it exists.
 */
class ADT_LiftIO_Example:

  type XYZReader[A] = Either[Throwable, A]

  given LiftIO[XYZReader] with
    override def liftIO[A](ioa: IO[A]): XYZReader[A] = ioa.attempt.unsafeRunSync()

  @Test
  def check(): Unit = {
    val xyzseq: String = read("aboba.txt").getOrElse(throw RuntimeException("Cannot read resources!"))
    println(XYZHandler.Handle(xyzseq)[Int])
  }

  def read(name: String)(using io: LiftIO[XYZReader]): XYZReader[String] =
    io.liftIO(IO(
      BufferedReader(InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(name), StandardCharsets.UTF_8)).readLine()
    ))

  trait XYZHandlerAlgebra[A]:
    def handle(xyzseq: String): A

  type XYZHandler = [A] => XYZHandlerAlgebra[A] ?=> A

  object XYZHandler:
    def Handle(xyzseq: String): XYZHandler =
      [A] => (alg: XYZHandlerAlgebra[A]) ?=> alg.handle(xyzseq)
    
  given XYZHandlerAlgebra[Int] with
    override def handle(xyzseq: String): Int = {
      val arr = xyzseq.toCharArray
      val arrl = arr.length
      var i = 0
      var x = 0
      for ch <- arr do
        if i < arrl - 1 && ch != arr(i + 1) then x = x + 1
        i = i + 1
      x
    }
      
