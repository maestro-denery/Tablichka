package io.denery.entityregistry.entity.invoke

import com.lmax.disruptor.dsl.Disruptor
import com.lmax.disruptor.util.DaemonThreadFactory
import com.lmax.disruptor.{BusySpinWaitStrategy, EventHandler, RingBuffer}
import io.denery.entityregistry.entity.CustomizableEntity
import io.denery.entityregistry.entity.invoke.CustomizableEntityEvent
import org.bukkit.Server
import org.bukkit.plugin.java.JavaPlugin
import org.foton.mts.Randomizer

import java.util

/**
 * ASK DENERY IF YOU WANT TO CHANGE SOMETHING IN THIS CLASS!
 *
 * Class created to perform customizable entity's creation in many ways. (behaviour iterating etc.)
 */
final class CustomizableEntityPerformer private():
  var disruptor: Option[Disruptor[CustomizableEntityEvent]] = None
  var performerBuffer: Option[RingBuffer[CustomizableEntityEvent]] = None

  def start(handlers: EventHandler[CustomizableEntityEvent]*): Unit = {
    val disruptor: Disruptor[CustomizableEntityEvent] = new Disruptor(
      () => CustomizableEntityEvent(),
      1024,
      DaemonThreadFactory.INSTANCE
    )
    handlers.foreach(handler => disruptor.handleEventsWith(handler))
    performerBuffer = Option(disruptor.start())
    this.disruptor = Option(disruptor)
  }

  def shutdown(): Unit = {
    disruptor.getOrElse(throw RuntimeException("Cannot get disruptor for shutting down! Please start it!")).shutdown()
  }

object CustomizableEntityPerformer:
  
  private var instance: CustomizableEntityPerformer = null

  def getInstance: CustomizableEntityPerformer = {
    if instance == null then
      instance = CustomizableEntityPerformer()
    instance
  }