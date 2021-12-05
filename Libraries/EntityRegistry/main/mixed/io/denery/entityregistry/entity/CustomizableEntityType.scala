package io.denery.entityregistry.entity

import com.ticxo.modelengine.api.model.ActiveModel
import io.denery.entityregistry.behaviour.BehaviourCategories.CommonBehaviourDictionary
import org.bukkit.entity.EntityType
import com.ticxo.modelengine.api.ModelEngineAPI

import java.util.Optional

class CustomizableEntityType(id: String,
                             originType: EntityType,
                             var behaviour: CommonBehaviourDictionary[Seq[CustomizableEntity[_] => Unit]]):

  var activeModel: ActiveModel = ModelEngineAPI.api.getModelManager.createActiveModel(id)

  def getID: Option[String] = Option(id)

  def getOriginType: Option[EntityType] = Option(originType)

  def getActiveModel: Option[ActiveModel] = Option(activeModel)

  def getBehaviour: Option[CommonBehaviourDictionary[Seq[CustomizableEntity[_] => Unit]]] = Option(behaviour)

  def setActiveModel(activeModel: ActiveModel): Unit = {
    this.activeModel = activeModel
  }

  def setBehaviour(behaviour: CommonBehaviourDictionary[Seq[CustomizableEntity[_] => Unit]]): Unit = {
    this.behaviour = behaviour
  }

