package org.foton.entities.scala

import io.denery.scala.behaviour.BehaviourCategories.CommonBehaviourDictionary
import io.denery.scala.behaviour.BehaviourCategories.BehaviourNodeDictionary.*
import io.denery.scala.behaviour.BehaviourCategories.BehaviourActionDictionary.*

object Behaviours:
  def greatHunger[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("aaa", ent => {

      }),
      Action("bbb", ent => {

      })
    )
