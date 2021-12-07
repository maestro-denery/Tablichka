package com.danikvitek

import com.danikvitek.waystones.WayStonesPlugin
import com.danikvitek.waystones.waystone.Waystone
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.{ChatColor, Location, World}

import java.util.UUID

package object waystones {
  def waystoneToLocation(waystone: Waystone): Location =
    new Location(
      waystone.world,
      waystone.x, waystone.y, waystone.z
    )

  def getWorldName(world: World): String = {
    val section = JavaPlugin.getPlugin(classOf[WayStonesPlugin]).getConfig.getConfigurationSection("world_names")
    if (section != null) try {
      val worldKey: String = section.getKeys(false).stream.filter((k: String) => UUID.fromString(k) == world.getUID).toList.get(0)
      section.getString(worldKey)
    } catch {
      case e: IndexOutOfBoundsException =>
        WayStonesPlugin.log(ChatColor.RED.toString + "world \"" + world.getName + "\" (" + world.getUID + ") does not have a name specified")
        world.getName
    }
    else {
      WayStonesPlugin.log(ChatColor.RED.toString + "config.yml does not contain section world_names")
      world.getName
    }
  }

  def toRadians(x: Double): Double = (x / 180d) * math.Pi

  def toDegrees(x: Double): Double = (x / math.Pi) * 180d
}
