package org.foton.entities

import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourActionDictionary.Action
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourNodeDictionary.ActionNode
import io.denery.entityregistry.behaviour.BehaviourCategories.CommonBehaviourDictionary

object Behaviours:
  def greatHunger[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("burrow-in-sand", ent => {

      }),
      Action("attack-player", ent => {

      })
    )
