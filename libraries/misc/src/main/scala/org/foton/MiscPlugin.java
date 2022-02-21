package org.foton;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public final class MiscPlugin extends JavaPlugin {
    private final Logger logger = this.getSLF4JLogger();

    @Override
    public void onEnable() {
        logger.info("Enabling Misc...");
    }

    @Override
    public void onDisable() {
        logger.info("Disabling Misc...");
    }
}
