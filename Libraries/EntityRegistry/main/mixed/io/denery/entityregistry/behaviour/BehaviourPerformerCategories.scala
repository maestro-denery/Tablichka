package io.denery.entityregistry.behaviour

import cats.Monad
import cats.effect.{ContextShift, Fiber, IO}
import io.denery.entityregistry.entity.CustomizableEntity
import org.bukkit.entity.LivingEntity

import scala.concurrent.ExecutionContext.Implicits.global

object BehaviourPerformerCategories:

  given ContextShift[IO] = IO.contextShift(global)

  import tofu.syntax.monadic._
  import tofu.syntax.raise._
  import tofu.Raise
/*
  trait BehaviourPerformerAlgebra[F[_]: ]:
    def perform(entity: CustomizableEntity[_])(using raise: Raise[F, BehaviourPerformError]): F[Unit]

  case class BehaviourPerformError(message: String = s"Cannot perform entity's behaviour") extends Throwable:
    override def getMessage: String = message

  given [F[_]: ContextShift]: BehaviourPerformerAlgebra[F] with
    override def perform(entity: CustomizableEntity[_])(using raise: Raise[F, BehaviourPerformError]): F[Unit] = {
      if (entity.entityType.getID.isDefined) {

        // TODO: Concurrent behaviour Performing with Fibers (see Experimentarium in EntityRegistry's tests)
      } else {
        raise.raise(BehaviourPerformError("EntityType's ID is null somehow!"))
      }
    } */