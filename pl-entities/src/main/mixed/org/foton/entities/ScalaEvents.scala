package org.foton.entities

import cats.effect.IO
import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.behaviour.BehaviourCategories
import io.denery.entityregistry.entity.CustomizableEntity
import io.denery.entityregistry.spawn.SpawnCategories.*
import io.denery.entityregistry.spawn.SpawnCategories
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.entity.Mob
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

import java.util.Optional

/**
 * Created for correct communication with very FP scala components.
 */
class ScalaEvents(plugin: JavaPlugin) extends Listener {
  import SpawnCategories.SpawnSettings.*
  import SpawnCategories.SpawnSettingsNode.*
  def spawnSettings[A]: CommonSpawnSettingsDictionary[A] =
    Node("root",
      Settings("default-spawner",DefaultSettings())
    )

  @EventHandler
  def onMove(e: PlayerMoveEvent): Unit = {
    //val loc: Location = e.getPlayer.getLocation
    //SpawnCategories.SpawnEngine.spawn[IO](spawnSettings[Int], plugin.getServer, loc, EntityTypeRegistry.getInstance().getRegisteredEntitiesList.asScala.toList)
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