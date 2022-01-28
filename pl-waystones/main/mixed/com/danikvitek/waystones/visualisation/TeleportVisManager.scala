package com.danikvitek.waystones.visualisation

import com.danikvitek.waystones.{SourceDestinationPair, WayStonesPlugin, waystoneToLocation, toRadians, toDegrees}
import org.bukkit.Location
import org.bukkit.block.BlockState
import org.bukkit.block.data.BlockData
import org.bukkit.entity.Player
import org.bukkit.scheduler.{BukkitRunnable, BukkitTask}

import scala.collection.immutable.Range

object TeleportVisManager {
  private final var plugin: Option[WayStonesPlugin] = None

  def init(plugin: WayStonesPlugin): Unit = {
    if (TeleportVisManager.plugin.isEmpty)
      TeleportVisManager.plugin = Option(plugin)
    else throw new IllegalStateException("TeleportVisManager is already initialised")
  }

  def startVisualisation(player: Player, sdp: SourceDestinationPair): Unit = {
    val srcLoc: Location = waystoneToLocation(sdp.getSource)
    val dstLoc: Location = waystoneToLocation(sdp.getDestination)
    sendVisualisation(player, srcLoc, dstLoc)
  }

  def cancelVisualisation(player: Player, sdp: SourceDestinationPair): Unit = {
    val srcLoc: Location = waystoneToLocation(sdp.getSource)
    sendVisualisation(player, srcLoc, srcLoc)
  }

  def sendVisualisation(player: Player, where: Location, what: Location): Unit = {
    if plugin.isEmpty then throw new RuntimeException("TeleportVisManager is not initialised")
    else plugin.map(_.getProtocolManager).foreach(protocolManager => {
      ((() => println(getBlocksAt(what).size)): BukkitRunnable).runTaskAsynchronously(plugin.get)
    }) // todo: implement
  }

  private def getBlocksAt(location: Location): Set[(Int, Int, Int)/*BlockState*/] = {
    (for { // todo: fix "Caused by: java.lang.IllegalStateException: Tile is null, asynchronous access?"
      r <- 5 to 50
      theta <- /*Range.BigDecimal.inclusive(0d, 180d, toDegrees(2d * math.acos(math.sqrt(r * r - 0.25) / r)))*/ 0 to 360 map (_.toDouble / 2d) map toRadians
      phi <- /*Range.BigDecimal.inclusive(0d, 360d, toDegrees(2d * math.acos(math.sqrt(r * r - 0.25) / r)))*/ 0 to 720 map (_.toDouble / 2d) map toRadians
    } yield /*location.getWorld.getBlockAt*/(
      math.round(location.getBlockX + r * math.sin(theta) * math.cos(phi)).toInt,
      math.round(location.getBlockY + r * math.cos(theta)).toInt,
      math.round(location.getBlockZ + r * math.sin(theta) * math.sin(phi)).toInt
    )/*.getState*/).toSet
  }
}