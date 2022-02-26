package dev.tablight.common.impl.base;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BasePlugin extends JavaPlugin {
	public static final Logger LOGGER = LoggerFactory.getLogger("Base-API");

	@Override
	public void onEnable() {
		LOGGER.info("Enabling Base-API");
	}

	@Override
	public void onDisable() {
		LOGGER.info("Disabling Base-API");
	}
}
