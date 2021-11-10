package org.tablichka;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Launcher extends JavaPlugin {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka");

    @Override
    public void onEnable() {
        logger.info("Starting Tablichka...");
    }

    @Override
    public void onDisable() {
        logger.info("Shutting Down Tablichka...");
    }
}
