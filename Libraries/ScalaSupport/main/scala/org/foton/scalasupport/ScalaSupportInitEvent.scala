package org.foton.scalasupport

import org.bukkit.event.{Event, HandlerList}
object ScalaSupportInitEvent:
  import org.bukkit.event.HandlerList
  private val HANDLERS = new HandlerList

  def getHandlerList() = HANDLERS

class ScalaSupportInitEvent extends Event:
  override def getHandlers: HandlerList = ScalaSupportInitEvent.HANDLERS