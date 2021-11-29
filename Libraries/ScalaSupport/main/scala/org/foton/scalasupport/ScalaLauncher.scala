package org.foton.scalasupport

import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.{Logger, LoggerFactory}

class ScalaLauncher extends JavaPlugin:
  
  val log: Logger = LoggerFactory.getLogger("Tablichka-Entities")
  
  override def onDisable(): Unit = {
    log.info("Starting ScalaSupport plugin with shaded standard Scala library in it!")
  }

  override def onEnable(): Unit = {
    log.info("Shutting down ScalaSupport plugin!")
  }
