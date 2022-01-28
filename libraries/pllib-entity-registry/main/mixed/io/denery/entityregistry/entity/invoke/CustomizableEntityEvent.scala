package io.denery.entityregistry.entity.invoke

import com.lmax.disruptor.EventFactory
import io.denery.entityregistry.entity.CustomizableEntity

/**
 * This is not a bukkit event, it is LMAX Disruptor's event for parallel handling actions with Customizable Entity.
 */
class CustomizableEntityEvent:
  private var entity: Option[CustomizableEntity] = None

  def getEntity(): Option[CustomizableEntity] = entity

  def setEntity(customizableEntity: CustomizableEntity): Unit = entity = Option(customizableEntity)