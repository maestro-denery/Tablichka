package io.denery.entityregistry.behaviour

import io.denery.entityregistry.entity.CustomizableEntity
import org.bukkit.entity.LivingEntity

import scala.util.Random

/**
 *  Fully-functional implementation for customizing entity's "Behaviour" (AI).
 *  Written in Tagless Final pattern of FP.
 */
object BehaviourCategories:
  // TODO: Monad support to algebras for handling exceptions/computing actions in algebras.

  type BehaviourNode = [A] => BehaviourNodeAlgebra[A] ?=> A
  type BehaviourAction = [A] => BehaviourActionAlgebra[A] ?=> A

  type BehaviourNodeDictionary[A] = BehaviourNodeAlgebra[A] ?=> A
  type BehaviourActionDictionary[A] = BehaviourActionAlgebra[A] ?=> A
  type BehaviourConditionalNodeDictionary[A] = BehaviourConditionalNodeAlgebra[A] ?=> A
  type CommonBehaviourDictionary[A] = (BehaviourNodeAlgebra[A], BehaviourConditionalNodeAlgebra[A], BehaviourActionAlgebra[A]) ?=> A

  // types used for dividing / shortening of values from algebras.
  
  // (I am too lazy to replace this type with map)
  type SeqOfActions = Seq[(String, CustomizableEntity => Unit)]

  trait BehaviourNodeAlgebra[A]:
    def actionNode(id: String, sub: Seq[A]): A

  trait BehaviourConditionalNodeAlgebra[A]:
    def conditionalActionNode(condition: CustomizableEntity => Boolean, sub: Seq[A]): A

  trait BehaviourActionAlgebra[A]:
    def action(id: String, action: CustomizableEntity => Unit): A

  object BehaviourNodeDictionary:
    def ActionNode[A](id: String, sub: A*): BehaviourNodeDictionary[A] =
      (alg: BehaviourNodeAlgebra[A]) ?=> alg.actionNode(id, sub)

  object BehaviourConditionalNodeDictionary:
    def ConditionalActionNode[A](condition: CustomizableEntity => Boolean, sub: A*): BehaviourConditionalNodeDictionary[A] =
      (alg: BehaviourConditionalNodeAlgebra[A]) ?=> alg.conditionalActionNode(condition, sub)

  object BehaviourActionDictionary:
    def Action[A](id: String, action: CustomizableEntity => Unit): BehaviourActionDictionary[A] =
      (alg: BehaviourActionAlgebra[A]) ?=> alg.action(id, action)

  given BehaviourNodeAlgebra[Int] with
    override def actionNode(id: String, sub: Seq[Int]): Int = sub.sum

  given BehaviourActionAlgebra[Int] with
    override def action(id: String, action: CustomizableEntity => Unit): Int = 1

  given BehaviourConditionalNodeAlgebra[Int] with
    override def conditionalActionNode(condition: CustomizableEntity => Boolean, sub: Seq[Int]): Int = sub.sum

  given BehaviourActionAlgebra[SeqOfActions] with
    override def action(id: String, action: CustomizableEntity => Unit): SeqOfActions =
      Seq((id, action))

  given BehaviourConditionalNodeAlgebra[SeqOfActions] with
    override def conditionalActionNode(condition: CustomizableEntity => Boolean, sub: Seq[SeqOfActions]): SeqOfActions =
      sub.flatten.map {action => {
        val func: CustomizableEntity => Unit = (entity: CustomizableEntity) => {
          if (condition(entity)) {
            action._2(entity)
          }
        }
        (action._1, func)
      }}

  given BehaviourNodeAlgebra[SeqOfActions] with
    override def actionNode(id: String, sub: Seq[SeqOfActions]): SeqOfActions = sub.flatten
