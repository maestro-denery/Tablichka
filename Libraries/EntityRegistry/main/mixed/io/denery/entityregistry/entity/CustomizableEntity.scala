package io.denery.entityregistry.entity

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.{ActiveModel, ModeledEntity}
import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.entity.CustomizableEntityType
import io.denery.entityregistry.behaviour.BehaviourCategories.CommonBehaviourDictionary
import org.bukkit.entity.LivingEntity
import org.slf4j.{Logger, LoggerFactory}

import java.util.Optional

/**
 * Class which instance represents entity existing in the world that you can highly customize with model,
 * Behaviour (AI) and much more...
 *
 * @param _entityType type of entity which you registered before.
 * @param _origin the origin entity which custom entity is based. (Ocelot, villager, etc.)
 * @tparam T origin entity's type. (maybe will be deleted later)
 */
class CustomizableEntity[T <: LivingEntity](_entityType: CustomizableEntityType,
                                            _origin: T):

  def modelEntity(): Unit = {
    val activeModel = ModelEngineAPI.api.getModelManager.createActiveModel(entityType.getID
      .getOrElse(throw RuntimeException("Please, set entity's ID to find model engine's model!")))
    val me = Optional.ofNullable(ModelEngineAPI.api.getModelManager.createModeledEntity(origin))
    if (me.isEmpty) throw new RuntimeException("Couldn't create modeled entity: " + origin.getName)
    val modeledEntity: ModeledEntity = me.get
    modeledEntity.addActiveModel(activeModel)
    modeledEntity.detectPlayers()
    modeledEntity.setInvisible(true)
  }

  def performBehaviour(): Unit = {
    //TODO: Perform behaviour using BehaviourPerformerCategories class with Fibers.
  }

  def entityType: CustomizableEntityType = _entityType

  def origin: T = _origin

object CustomizableEntity:
  private val logger = LoggerFactory.getLogger("EntityRegistryLib")