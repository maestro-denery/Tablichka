package org.foton.scalasupport

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.{Logger, LoggerFactory}

class ScalaLauncher extends JavaPlugin:
  
  val log: Logger = LoggerFactory.getLogger("ScalaSupport")
  
  override def onEnable(): Unit = {
    val event: ScalaSupportInitEvent = ScalaSupportInitEvent()
    Bukkit.getPluginManager.callEvent(event)
    log.info("Starting ScalaSupport plugin with shaded standard Scala library in it!")
  }

  override def onDisable(): Unit = {
    val event: ScalaSupportDownEvent = ScalaSupportDownEvent()
    Bukkit.getPluginManager.callEvent(event)
    log.info("Shutting down ScalaSupport plugin!")
  }
