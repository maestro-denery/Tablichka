package org.foton.entities

import com.ticxo.modelengine.api.util.ConfigManager
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourActionDictionary.Action
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourConditionalNodeDictionary.ConditionalActionNode
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourNodeDictionary.ActionNode
import io.denery.entityregistry.behaviour.BehaviourCategories.CommonBehaviourDictionary
import org.foton.entities.mobs.GreatHunger.logger

/**
 * Create Foton's mobs there, current list of mobs:
 *
 * - GreatHunger (MobC)
 */
package object mobs {
  def greatHunger[A]: CommonBehaviourDictionary[A] =
    ActionNode("root",
      Action("burrow-in-sand", ent => {
        logger.info("burrowing...")
        ent.entityType.getActiveModel.getOrElse(throw RuntimeException("Cannot get entity's ActiveModel"))
          .setAnimationMode(ConfigManager.AnimationMode.get("Activate"))
      }),
      Action("attack", ent => {
        logger.info("attacking...")
        ent.entityType.getActiveModel.getOrElse(throw RuntimeException("Cannot get entity's ActiveModel"))
          .setAnimationMode(ConfigManager.AnimationMode.get("Fight"))
      }),
      ConditionalActionNode(
        ent => ent.origin.isSwimming,
        Action("aboba", ent => {
          logger.info(s"swimming: ${ent.entityType.getID}")
        })
      )
    )

}
