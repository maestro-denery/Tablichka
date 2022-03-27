package dev.tablight.dependencies;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DependencyManager extends JavaPlugin {
	private static final Logger DM_LOGGER = LoggerFactory.getLogger("DependencyManager");
	
	@Override
	public void onEnable() {
		DM_LOGGER.info("Tablight dependency manager successfully initialized.");
	}

	@Override
	public void onDisable() {
		DM_LOGGER.info("Disabling Tablight dependency manager...");
	}
}
