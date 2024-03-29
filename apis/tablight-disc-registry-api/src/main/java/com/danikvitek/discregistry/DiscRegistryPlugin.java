package com.danikvitek.discregistry;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public final class DiscRegistryPlugin extends JavaPlugin {
    private final Logger logger = this.getSLF4JLogger(); 
    
    @Override
    public void onEnable() {
        logger.info("Enabling DiscRegistry...");
    }

    @Override
    public void onDisable() {
        logger.info("Disabling DiscRegistry...");
    }
}
