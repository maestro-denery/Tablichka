package org.foton.entities.scala

import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.entity.CustomizableEntity
import io.denery.entityregistry.spawn.CustomizableSpawn
import io.denery.scala.behaviour.BehaviourCategories
import org.bukkit.Location
import org.bukkit.entity.Mob
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.plugin.java.JavaPlugin

import java.util.Optional
import scala.jdk.CollectionConverters

/**
 * Created for correct communication with scala components and to use all power of fp.
 */
class ScalaEvents(plugin: JavaPlugin) extends Listener {
  val spawnBuilder = CustomizableSpawn.newBuilder()
    .setEntities(EntityTypeRegistry.getInstance().getRegisteredEntitiesList)
    .setMaxPerChunk(40)
    .build()


  @EventHandler
  def onMove(e: PlayerMoveEvent): Unit = {
    //val loc: Location = e.getPlayer.getLocation;
    //val entities: java.util.List[Optional[CustomizableEntity[_]]] = spawnBuilder.apply(plugin.getServer(), loc)
    /*
    entities.foreach(opt => {
      if (opt.isPresent) {
        var entity: CustomizableEntity[_] = opt.get()
        entity.modelEntity()
        //entity.setBehaviour(greatHungerBehaviour)
      }
    })
    */

  }
}