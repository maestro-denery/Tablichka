package dev.tablight.common.impl.entityregistry;

import dev.tablight.common.base.global.GlobalGroupContainer;

import dev.tablight.common.base.dataaddon.DataAddonBootstrap;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public final class EntityRegistryPlugin extends JavaPlugin {
    private final Logger logger = this.getSLF4JLogger();

    @Override
    public void onEnable() {
		var container = GlobalGroupContainer.getInstance();
		DataAddonBootstrap bootstrap = new DataAddonBootstrap();
		//bootstrap.bootstrapRegistries();
		//bootstrap.getTypeRegistry()
        logger.info("Enabling DiscRegistry...");
    }

    @Override
    public void onDisable() {
        logger.info("Disabling DiscRegistry...");
    }
}
