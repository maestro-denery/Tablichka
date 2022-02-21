package org.foton.scalasupport

import org.bukkit.event.{Event, HandlerList}
import org.foton.scalasupport.OnScalaSupportUnloadEvent.HANDLERS
@deprecated
class OnScalaSupportUnloadEvent extends Event {
  override def getHandlers: HandlerList = HANDLERS
}

object OnScalaSupportUnloadEvent {
  private val HANDLERS = new HandlerList

  def getHandlerList: HandlerList = HANDLERS
}