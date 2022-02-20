package org.foton.entities.commands

import io.denery.entityregistry.EntityTypeRegistry
import io.denery.entityregistry.entity.CustomizableEntity
import io.denery.entityregistry.entity.invoke.CustomizableEntityPerformer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.{LivingEntity, Player}

import java.util.Optional

object ERSpawn:
  def command(sender: CommandSender, args: Array[String]): Boolean =
    if sender.hasPermission("foton.commands.erspawn") && args.length == 1 then
      if !EntityTypeRegistry.getInstance.getRegisteredEntities.containsKey(args(0)) then
        sender.sendMessage(Component.text("Cannot find registered entities!").color(NamedTextColor.GRAY))
        return false

      val entityType = EntityTypeRegistry.getInstance.getRegisteredEntities.get(args(0))
      val player = sender.asInstanceOf[Player]
      entityType.spawn(player.getLocation)

      return true
    false