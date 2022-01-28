package org.foton.entities.commands

import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.entity.CustomizableEntity
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.slf4j.{Logger, LoggerFactory}
import org.bukkit.command.{Command, CommandSender, TabExecutor}
import org.bukkit.entity.{EntityType, LivingEntity, Player}
import org.bukkit.scheduler.BukkitRunnable
import org.foton.utils.StringUtils

import java.util
import java.util.{Collections, Optional}

class CommandManager private() extends TabExecutor:
  val logger: Logger = LoggerFactory.getLogger("Foton-Entities")

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    if args.length == 1 then
      command.getName match {
        case "erspawn" => ERSpawn.command(sender, args)
        case _ => false;
      }
    else false
  }

  override def onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array[String]): util.List[String] =
    if args.length == 1 then
      StringUtils.copyPartialInnerMatches(args(0),
        command.getName match {
          case "erspawn" => EntityTypeRegistry.getInstance.getRegisteredEntities.keySet.stream.toList
          case _ => Collections.emptyList()
        }
      )
    else Collections.emptyList()
end CommandManager

object CommandManager:
  private val instance = null

  def getInstance: CommandManager = {
    if (instance == null) return new CommandManager
    instance
  }