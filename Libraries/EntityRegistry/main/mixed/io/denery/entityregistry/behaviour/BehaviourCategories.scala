package io.denery.entityregistry.behaviour

import io.denery.entityregistry.entity.CustomizableEntity
import org.bukkit.entity.LivingEntity

import scala.util.Random

/**
 *  Fully-functional implementation for customizing entity's "Behaviour" (AI).
 *  Written in "extended" Tagless Final pattern of FP.
 *  "extended" means that it's not a usual Tagless Final, it is extended with
 *  additional mathematical "theory of categories" components.
 */
object BehaviourCategories:
  // TODO: Monad support to algebras for handling exceptions/computing actions in algebras.

  type BehaviourNode = [A] => BehaviourNodeAlgebra[A] ?=> A
  type BehaviourAction = [A] => BehaviourActionAlgebra[A] ?=> A

  type BehaviourNodeDictionary[A] = BehaviourNodeAlgebra[A] ?=> A
  type BehaviourActionDictionary[A] = BehaviourActionAlgebra[A] ?=> A
  type CommonBehaviourDictionary[A] = (BehaviourNodeAlgebra[A], BehaviourActionAlgebra[A]) ?=> A

  trait BehaviourNodeAlgebra[A]:
    def actionNode(id: String, sub: Seq[A]): A

  trait BehaviourActionAlgebra[A]:
    def action(id: String, action: CustomizableEntity[_] => Unit): A

  object BehaviourNodeDictionary:
    def ActionNode[A](id: String, sub: A*): BehaviourNodeDictionary[A] =
      (alg: BehaviourNodeAlgebra[A]) ?=> alg.actionNode(id, sub)

  object BehaviourActionDictionary:
    def Action[A](id: String, action: CustomizableEntity[_] => Unit): BehaviourActionDictionary[A] =
      (alg: BehaviourActionAlgebra[A]) ?=> alg.action(id, action)

  given BehaviourNodeAlgebra[Int] with
    override def actionNode(id: String, sub: Seq[Int]): Int = sub.sum

  given BehaviourActionAlgebra[Int] with
    override def action(id: String, action: CustomizableEntity[_] => Unit): Int = 1

  given BehaviourActionAlgebra[Seq[CustomizableEntity[_] => Unit]] with
    override def action(id: String, action: CustomizableEntity[_] => Unit): Seq[CustomizableEntity[_] => Unit] =
      Seq(action)

  given BehaviourNodeAlgebra[Seq[CustomizableEntity[_] => Unit]] with
    override def actionNode(id: String, sub: Seq[Seq[CustomizableEntity[_] => Unit]]): Seq[CustomizableEntity[_] => Unit] = sub.flatten