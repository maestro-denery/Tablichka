package org.tablichka.entities.commands;

import io.denery.EntityRegistry;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EntitiesCommands implements CommandExecutor, TabExecutor {
    private EntitiesCommands() {}

    private static EntitiesCommands instance;
    public static EntitiesCommands getInstance() {
        if (instance == null) return new EntitiesCommands();
        return instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        return switch (command.getName()) {
            case "ERSpawn" -> ((Supplier<Boolean>) () -> {
                if (commandSender.hasPermission("tablichka.commands.erspawn")) {
                    if (args.length == 0) return false;
                    if (args.length == 1) {
                        if (!EntityRegistry.getInstance().getRegisteredEntities().containsKey(args[0])) {
                            commandSender.sendMessage(Color.GRAY + "Cannot find registered entities!");
                            return false;
                        }

                        return true;
                    }
                    return false;
                } else {
                    commandSender.sendMessage(Color.GRAY + "You cannot spawn Modeled entity!");
                }
                return true;
            }).get();

            default -> false;
        };
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] strings) {
        return switch (command.getName()) {
            case "ERSpawn" -> ((Supplier<List<String>>) () -> {
                return EntityRegistry.getInstance().getRegisteredEntities().keySet().stream().toList();
            }).get();

            default -> null;
        };
    }
}
