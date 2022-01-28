package org.foton.scalasupport

import org.bukkit.event.{Event, HandlerList}
import org.foton.scalasupport.OnScalaSupportLoadEvent.HANDLERS

class OnScalaSupportLoadEvent extends Event {
  override def getHandlers: HandlerList = HANDLERS
}

object OnScalaSupportLoadEvent {
  private val HANDLERS = new HandlerList

  def getHandlerList: HandlerList = HANDLERS
}