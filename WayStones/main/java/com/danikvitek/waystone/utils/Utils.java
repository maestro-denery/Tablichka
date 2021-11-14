package com.danikvitek.waystone.utils;

import com.danikvitek.waystone.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {
    public static String getWorldName(World world) throws IllegalStateException {
        ConfigurationSection section = Main.getPlugin(Main.class).getConfig().getConfigurationSection("world_names");
        if (section != null) {
            try {
                String worldKey = section.getKeys(false).stream().filter(k -> UUID.fromString(k).equals(world.getUID())).collect(Collectors.toList()).get(0);
                return section.getString(worldKey);
            } catch (IndexOutOfBoundsException e) {
                Main.log(ChatColor.RED + "world \"" + world.getName() + "\" (" + world.getUID() + ") does not have a name specified");
                return world.getName();
            }
        }
        else {
            Main.log(ChatColor.RED + "config.yml does not contain section world_names");
            return world.getName();
        }
    }
}
