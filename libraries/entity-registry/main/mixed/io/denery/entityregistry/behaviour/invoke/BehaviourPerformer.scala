package io.denery.entityregistry.behaviour.invoke

import com.lmax.disruptor.{EventHandler, EventTranslator, RingBuffer}
import com.lmax.disruptor.dsl.Disruptor
import com.lmax.disruptor.util.DaemonThreadFactory
import io.denery.entityregistry.behaviour.invoke.BehaviourPerformer.handler
import io.denery.entityregistry.entity.CustomizableEntity
import io.denery.entityregistry.entity.invoke.CustomizableEntityEvent
import org.foton.mts.Randomizer
import org.slf4j.LoggerFactory
import java.util.concurrent.{ExecutorService, Executors}

/**
 * ASK DENERY IF YOU WANT TO CHANGE SOMETHING IN THIS CLASS!
 * 
 * Class created for performing entities' behaviours, highly loaded part of entity registry's plugin. 
 */
class BehaviourPerformer private (): 
  var disruptor: Option[Disruptor[BehaviourInvocationEvent]] = None
  var invocationBuffer: Option[RingBuffer[BehaviourInvocationEvent]] = None
  val performerExecutor: ExecutorService = Executors.newCachedThreadPool()
  def start(): Unit = {
    val disruptor: Disruptor[BehaviourInvocationEvent] = new Disruptor(
      () => BehaviourInvocationEvent(),
      1024,
      DaemonThreadFactory.INSTANCE
    )
    disruptor.handleEventsWith(handler)
    invocationBuffer = Option(disruptor.start())
    this.disruptor = Option(disruptor)
  }
  
  def shutdown(): Unit = {
    disruptor.getOrElse(throw RuntimeException("Cannot get disruptor for shutting down! Please start it!")).shutdown()
    performerExecutor.shutdown()
  }
  
  /**
   * Used by customizable entity performer, pushes events from c-entity creation to perform its behaviour.
   */
  val behaviourFromEntityHandler: EventHandler[CustomizableEntityEvent] =
    (event: CustomizableEntityEvent, seq: Long, endOfBatch: Boolean) => 
      event.getEntity() match {
        case Some(entity) =>
          BehaviourPerformer.getInstance.invocationBuffer match {
            case Some(invBuffer) =>
              performerExecutor.execute(() => {
                // Iteration of publishing behaviour invocations.
                while (!entity.origin.isDead) {
                  invBuffer.publishEvent(new EventTranslator[BehaviourInvocationEvent] {
                    override def translateTo(event: BehaviourInvocationEvent, l: Long): Unit = {
                      event.setInvocation((entity, Randomizer.getRandom(entity.entityType.behaviour)._2))
                    }
                  })
                }
              })
            case None => {}
          }
        case None => {}
      }

object BehaviourPerformer:
  private val logger = LoggerFactory.getLogger("EntityRegistryLib")
  private var instance: BehaviourPerformer = null

  def getInstance: BehaviourPerformer = {
    if instance == null then
      instance = BehaviourPerformer()
    instance
  }

  private val handler: EventHandler[BehaviourInvocationEvent] = (event: BehaviourInvocationEvent, seq: Long, endOfBatch: Boolean) => {
      event.getInvocation() match {
        case Some(invocation) => {}
        case None => {}
      }
  }
