package com.danikvitek.waystone.misc.utils;

import com.danikvitek.waystone.WayStonesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;
import java.util.stream.Collectors;

public class MiscUtils {
    public static String getWorldName(World world) throws IllegalStateException {
        ConfigurationSection section = WayStonesPlugin.getPlugin(WayStonesPlugin.class).getConfig().getConfigurationSection("world_names");
        if (section != null) {
            try {
                String worldKey = section.getKeys(false).stream().filter(k -> UUID.fromString(k).equals(world.getUID())).collect(Collectors.toList()).get(0);
                return section.getString(worldKey);
            } catch (IndexOutOfBoundsException e) {
                WayStonesPlugin.log(ChatColor.RED + "world \"" + world.getName() + "\" (" + world.getUID() + ") does not have a name specified");
                return world.getName();
            }
        }
        else {
            WayStonesPlugin.log(ChatColor.RED + "config.yml does not contain section world_names");
            return world.getName();
        }
    }
}
