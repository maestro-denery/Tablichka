package org.foton.scalasupport

import org.bukkit.event.{Event, HandlerList}
// TODO: Event on loading scala support.
class OnScalaSupportLoadEvent extends Event {

  import org.bukkit.event.HandlerList
  private val HANDLERS = new HandlerList

  override def getHandlers: HandlerList = HANDLERS
}

object OnScalaSupportLoadEvent extends Event:

  def getHandlerList() = HANDLERS