package org.foton.scalasupport

import org.bukkit.event.{Event, HandlerList}

object ScalaSupportDownEvent:
  import org.bukkit.event.HandlerList
  private val HANDLERS = new HandlerList

  def getHandlerList() = HANDLERS

class ScalaSupportDownEvent extends Event:
  override def getHandlers: HandlerList = ScalaSupportDownEvent.HANDLERS
