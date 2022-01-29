package org.foton.entities

import com.ticxo.modelengine.api.event.ModelEngineInitializeEvent
import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.behaviour.invoke.BehaviourPerformer
import io.denery.entityregistry.entity.invoke.CustomizableEntityPerformer
import org.bukkit.Server
import org.bukkit.event.{EventHandler, Listener}
import org.foton.architecture.Launcher
import org.bukkit.plugin.java.JavaPlugin
import org.foton.entities.commands.CommandManager
import org.foton.entities.mobs.GreatHunger
import org.slf4j.{Logger, LoggerFactory}

import java.util.Objects

final class FotonEntities extends JavaPlugin with Launcher:
  private val performer = CustomizableEntityPerformer.getInstance
  private val behaviourPerformer = BehaviourPerformer.getInstance

  override def loadCommands(): Unit = Objects.requireNonNull(getCommand("erspawn")).setExecutor(CommandManager.getInstance)

  override def loadEvents(): Unit = getServer.getPluginManager.registerEvents(FEEvents(), this)

  override def onEnable(): Unit = {
    FotonEntities.logger.info("Loading Entities Module.")
    FotonEntities.logger.info("Version dev-20")

    loadEvents()
    loadCommands()
  }

  override def onDisable(): Unit = {
    performer.shutdown()
    behaviourPerformer.shutdown()
    FotonEntities.logger.info("Shutting down Foton Entities...")
  }

  private class FEEvents extends Listener:
    @EventHandler
    def onModelEngineGeneratorLoad(e: ModelEngineInitializeEvent): Unit = {
      EntityTypeRegistry.newRegistry.register(GreatHunger.greatHungerType).apply

      val server = getServer
      server.getPluginManager.registerEvents(new ScalaEvents(FotonEntities.this), FotonEntities.this)

      // !WARNING! DO NOT TOUCH CODE UNDER THIS COMMENT, ASK DENERY IF YOU HAVE QUESTIONS.
      performer.start(behaviourPerformer.behaviourFromEntityHandler)
      behaviourPerformer.start()
    }

object FotonEntities:
  val logger: Logger = LoggerFactory.getLogger("FotonEntities")