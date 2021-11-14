package org.tablichka.entities.commands;

import io.denery.entityregistry.EntityTypeRegistry;
import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tablichka.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class EntitiesCommands implements TabExecutor {
    private EntitiesCommands() {
    }

    public static final Logger logger = LoggerFactory.getLogger("Tablichka-Entities");

    private static EntitiesCommands instance;

    public static EntitiesCommands getInstance() {
        if (instance == null) return new EntitiesCommands();
        return instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        return switch (command.getName()) {
            case "erspawn" -> ((Supplier<Boolean>) () -> {
                if (commandSender.hasPermission("tablichka.commands.erspawn")) {
                    if (args.length == 0) return false;
                    if (args.length == 1) {
                        if (!EntityTypeRegistry.getInstance().getRegisteredEntities().containsKey(args[0])) {
                            commandSender.sendMessage(Component.text("Cannot find registered entities!")
                                    .color(NamedTextColor.GRAY));
                            return false;
                        }

                        AbstractCustomizableEntityType type = EntityTypeRegistry.getInstance().getRegisteredEntities().get(args[0]);
                        Player player = (Player) commandSender;
                        Optional<EntityType> optionalEntityType = type.getOriginType();
                        if (optionalEntityType.isPresent()) {
                            Optional<Class<? extends Entity>> entityClass = Optional.ofNullable(type.getOriginType().orElseThrow().getEntityClass());
                            LivingEntity entity = (LivingEntity) player.getWorld().spawn(player.getLocation(), entityClass.orElseThrow());
                            CustomizableEntity<?> customizableEntity = new CustomizableEntity(args[0], entity);
                            customizableEntity.modelEntity();
                            return true;
                        }

                        return false;
                    }
                    return false;
                }
                commandSender.sendMessage(Component.text("You cannot spawn Modeled entity!")
                        .color(NamedTextColor.GRAY));
                return true;
            }).get();

            default -> false;
        };
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        return StringUtils.copyPartialInnerMatches(
                args[1],
                switch (command.getName()) {
                    case "erspawn" -> EntityTypeRegistry.getInstance().getRegisteredEntities().keySet().stream().toList();

                    default -> Collections.emptyList();
                });
    }
}
