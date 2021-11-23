package org.foton.fp

trait Monad[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
}
