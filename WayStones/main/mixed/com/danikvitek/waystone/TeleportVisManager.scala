package com.danikvitek.waystone

import com.comphenix.protocol.ProtocolManager
import org.bukkit.Location
import org.bukkit.entity.Player

object TeleportVisManager {
  private final var plugin: Option[WayStonesPlugin] = None

  def init(plugin: WayStonesPlugin): Unit = {
    if (TeleportVisManager.plugin.isEmpty)
      TeleportVisManager.plugin = Option(plugin)
    else throw new IllegalStateException("TeleportVisManager is already initialised")
  }

  def startVisualisation(player: Player): Unit = {
    val srcDstPair = Option(SourceDestinationPair.getByPlayer(player))
    srcDstPair.foreach(sdp => {
      val srcLoc: Location = waystoneToLocation(sdp.getSource)
      val dstLoc: Location = waystoneToLocation(sdp.getDestination)
      sendVisualisation(player, srcLoc, dstLoc)
    })
  }

  def cancelVisualisation(player: Player): Unit = {
    val srcDstPair = Option(SourceDestinationPair.getByPlayer(player))
    srcDstPair.foreach(sdp => {
      val srcLoc: Location = waystoneToLocation(sdp.getSource)
      sendVisualisation(player, srcLoc, srcLoc)
    })
  }

  def sendVisualisation(player: Player, where: Location, what: Location): Unit = {
    plugin.map(_.protocolManager).foreach(protocolManager => {

    })
  }
}