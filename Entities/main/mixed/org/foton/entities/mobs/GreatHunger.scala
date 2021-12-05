package org.foton.entities.mobs

import com.ticxo.modelengine.api.util.ConfigManager
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourActionDictionary.Action
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourNodeDictionary.ActionNode
import io.denery.entityregistry.behaviour.BehaviourCategories.CommonBehaviourDictionary
import io.denery.entityregistry.entity.{CustomizableEntity, CustomizableEntityType}
import org.bukkit.entity.EntityType

// TODO: full described Behaviour (AI)

object GreatHunger:
  val greatHungerType: CustomizableEntityType = new CustomizableEntityType("mobc", EntityType.PIG, 
    greatHunger[Seq[CustomizableEntity[_] => Unit]])
  
  def greatHunger[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("burrow-in-sand", ent => {
        ent.entityType.getActiveModel.getOrElse(throw RuntimeException("Cannot get entity's ActiveModel"))
          .setAnimationMode(ConfigManager.AnimationMode.get("Activate"))
      }),
      Action("attack", ent => {
        ent.entityType.getActiveModel.getOrElse(throw RuntimeException("Cannot get entity's ActiveModel"))
          .setAnimationMode(ConfigManager.AnimationMode.get("Fight"))
      })
    )
