package io.denery.entityregistry.entity

import com.ticxo.modelengine.api.model.ActiveModel
import io.denery.entityregistry.behaviour.BehaviourCategories.{CommonBehaviourDictionary, SeqOfActions}
import org.bukkit.Location
import org.bukkit.entity.{EntityType, LivingEntity}
import com.ticxo.modelengine.api.ModelEngineAPI
import io.denery.entityregistry.entity.invoke.CustomizableEntityPerformer

import java.util.Optional

class CustomizableEntityType(id: String,
                             originType: EntityType,
                             var behaviour: CommonBehaviourDictionary[SeqOfActions]):

  var activeModel: ActiveModel = ModelEngineAPI.api.getModelManager.createActiveModel(id)

  def getID: Option[String] = Option(id)

  def getOriginType: Option[EntityType] = Option(originType)

  def getActiveModel: Option[ActiveModel] = Option(activeModel)

  def getBehaviour: Option[CommonBehaviourDictionary[SeqOfActions]] = Option(behaviour)

  def setActiveModel(activeModel: ActiveModel): Unit = {
    this.activeModel = activeModel
  }

  def setBehaviour(behaviour: CommonBehaviourDictionary[SeqOfActions]): Unit = {
    this.behaviour = behaviour
  }

  def spawn(location: Location): CustomizableEntity =
      val entity: LivingEntity = location.getWorld.spawn(location, originType.getEntityClass).asInstanceOf[LivingEntity]
      val customizableEntity = CustomizableEntity(this, entity)
      customizableEntity.modelEntity()
      // Adding entity for performing its behaviour.
      val behaviourPerformerManager = CustomizableEntityPerformer.getInstance
      behaviourPerformerManager.performerBuffer match {
        case Some(value) => value.publishEvent((event, sequence) => event.setEntity(customizableEntity))
        case None => {}
      }
      customizableEntity

