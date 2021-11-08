package org.tablichka;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Launcher extends JavaPlugin {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka");

    @Override
    public void onEnable() {
        logger.info("Starting Tablet...");

    }

    @Override
    public void onDisable() {
        logger.info("Shutting Down Tablet...");
    }
}
