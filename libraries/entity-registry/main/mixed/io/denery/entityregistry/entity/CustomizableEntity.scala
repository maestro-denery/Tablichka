package io.denery.entityregistry.entity

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.{ActiveModel, ModeledEntity}
import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.entity.CustomizableEntityType
import org.bukkit.entity.{LivingEntity, Entity}
import de.tr7zw.nbtapi.NBTEntity

import java.util.Optional
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Class which instance represents entity existing in the world that you can highly customize with model,
 * Behaviour (AI) and much more...
 *
 * @param _entityType type of entity which you registered before.
 * @param _origin the origin entity which custom entity is based. (Ocelot, villager, etc.)
 */
class CustomizableEntity(_entityType: CustomizableEntityType, _origin: LivingEntity):

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

  def tag(): Unit = {
    val nbtent: NBTEntity = NBTEntity(_origin.asInstanceOf[Entity])
    //nbtent.setString()
  }

  def origin: LivingEntity = _origin

  def entityType: CustomizableEntityType = _entityType