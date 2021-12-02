package org.foton.entities

import com.ticxo.modelengine.api.util.ConfigManager
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourActionDictionary.Action
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourNodeDictionary.ActionNode
import io.denery.entityregistry.behaviour.BehaviourCategories.CommonBehaviourDictionary

object Behaviours:
  def greatHunger[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("burrow-in-sand", ent => {
        ent.getType.getActiveModel.ifPresent(s => {
          s.setAnimationMode(ConfigManager.AnimationMode.get("Activate"))
        })
      }),
      Action("attack", ent => {
        ent.getType.getActiveModel.ifPresent(s => {
          s.setAnimationMode(ConfigManager.AnimationMode.get("Fight"))
        })
      })
    )
