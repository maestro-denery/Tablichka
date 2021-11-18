package io.denery.scala.spawn

import io.denery.entityregistry.entity.{AbstractCustomizableEntityType, CustomizableEntity}
import org.bukkit.Server
import org.bukkit.Location
import reactor.core.publisher.Flux
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Random

case class CustomizableSpawn (delay: Int = 0,
                              maxPerChunk: Int = 20,
                              types: List[AbstractCustomizableEntityType]
                              = List.empty[AbstractCustomizableEntityType]) extends ((Server, Location) => Flux[Option[CustomizableEntity[_]]]) {

  private val logger: Logger  = LoggerFactory.getLogger("EntityRegistryLib")

  override def apply(server: Server, location: Location): Flux[Option[CustomizableEntity[_]]] = {
    Flux.create(s => {
      if (types.size < 1) logger.info("There is no entity types registered! please, register it!")
      var entityType = types.take(Random.nextInt(types.size))
    })
  }

}
