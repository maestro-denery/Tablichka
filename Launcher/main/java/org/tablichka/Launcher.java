package org.tablichka;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tablichka.architecture.Bootstrap;
import org.tablichka.discs.DiscsBootstrap;
import org.tablichka.entities.EntitiesBootstrap;

public final class Launcher extends JavaPlugin {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka");

    @Override
    public void onEnable() {
        logger.info("Starting Tablichka...");
        Bootstrap entitiesBootstrap = new EntitiesBootstrap(this);
        entitiesBootstrap.bootstrap();
        Bootstrap discsBootstrap = new DiscsBootstrap(this);
        discsBootstrap.bootstrap();
    }

    @Override
    public void onDisable() {
        logger.info("Shutting Down Tablichka...");
    }
}
