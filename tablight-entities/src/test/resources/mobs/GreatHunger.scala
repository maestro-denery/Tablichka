package org.foton.entities.mobs

import com.ticxo.modelengine.api.util.ConfigManager
import io.denery.entityregistry.behaviour.BehaviourCategories.{CommonBehaviourDictionary, SeqOfActions}
import io.denery.entityregistry.entity.{CustomizableEntity, CustomizableEntityType}
import org.bukkit.entity.EntityType
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourActionDictionary.Action
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourNodeDictionary.ActionNode
import io.denery.entityregistry.behaviour.BehaviourCategories.BehaviourConditionalNodeDictionary.ConditionalActionNode
import org.slf4j.{Logger, LoggerFactory}
// TODO: full described Behaviour (AI)

object GreatHunger:
  val logger: Logger = LoggerFactory.getLogger("FotonEntities")

  val greatHungerType: CustomizableEntityType = new CustomizableEntityType("mobc", EntityType.PIG, 
    greatHunger)
  
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
