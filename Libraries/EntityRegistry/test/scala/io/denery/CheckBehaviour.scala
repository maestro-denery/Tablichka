package io.denery

import org.junit.Test
import io.denery.scala.behaviour.BehaviourCategories
import io.denery.scala.behaviour.BehaviourCategories.*
import io.denery.scala.behaviour.BehaviourCategories.BehaviourNodeDictionary.*
import io.denery.scala.behaviour.BehaviourCategories.BehaviourActionDictionary.*

class CheckBehaviour:

  def sample[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("a", s => s.remove()),
      ActionNode("sub-a",
        Action("b", s => s.remove()),
        Action("c", s => s.remove())
      )
    )

  @Test
  def check: Unit = {
    println(sample[Int])
  }
