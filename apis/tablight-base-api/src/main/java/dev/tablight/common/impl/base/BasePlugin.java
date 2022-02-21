package dev.tablight.common.impl.base;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BasePlugin extends JavaPlugin {
	public static final Logger logger = LoggerFactory.getLogger("Base-API");

	@Override
	public void onEnable() {
		logger.info("Enabling Base-API");
	}

	@Override
	public void onDisable() {
		logger.info("Disabling Base-API");
	}
}
