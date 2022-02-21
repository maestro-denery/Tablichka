package org.foton.scalasupport

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import org.slf4j.{Logger, LoggerFactory}

@deprecated("There is a better way to support scala in project.")
class ScalaLauncher extends JavaPlugin:
  
  val log: Logger = LoggerFactory.getLogger("ScalaSupport")

  override def onEnable(): Unit = {
    log.info("Starting ScalaSupport plugin with shaded standard Scala library in it!")
    Bukkit.getPluginManager.callEvent(new OnScalaSupportLoadEvent)
  }
  
  override def onDisable(): Unit = {
    log.info("Shutting down ScalaSupport plugin!")
    Bukkit.getPluginManager.callEvent(new OnScalaSupportUnloadEvent)
  }