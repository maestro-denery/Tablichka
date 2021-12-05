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

class Commands private () extends TabExecutor:
  val logger: Logger = LoggerFactory.getLogger("Foton-Entities")

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    command.getName match {
      case "erspawn" =>
        if sender.hasPermission("foton.commands.erspawn") then
          if args.length == 1 then
            if !EntityTypeRegistry.getInstance.getRegisteredEntities.containsKey(args(0)) then
              sender.sendMessage(Component.text("Cannot find registered entities!").color(NamedTextColor.GRAY))
              return false

            val entityType = EntityTypeRegistry.getInstance.getRegisteredEntities.get(args(0))
            val player = sender.asInstanceOf[Player]
            val optionalEntityType = entityType.getOriginType
            if optionalEntityType.isDefined then
              val entityClass = Optional.ofNullable(entityType.getOriginType.getOrElse(throw RuntimeException("Cannot get entity's origin type to spawn it!")).getEntityClass)
              val entity: LivingEntity = player.getWorld.spawn(player.getLocation, entityClass.orElseThrow).asInstanceOf[LivingEntity]
              val customizableEntity = CustomizableEntity[LivingEntity](entityType, entity)
              customizableEntity.modelEntity()

              return true

          return false
        false
      case _ => false;
    }
  }

  override def onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array[String]): util.List[String] =
    StringUtils.copyPartialInnerMatches(args(0),
      command.getName match {
        case "erspawn" => EntityTypeRegistry.getInstance.getRegisteredEntities.keySet.stream.toList
        case _ => Collections.emptyList()
      }
    )

object Commands:
  private val instance = null

  def getInstance: Commands = {
    if (instance == null) return new Commands
    instance
  }