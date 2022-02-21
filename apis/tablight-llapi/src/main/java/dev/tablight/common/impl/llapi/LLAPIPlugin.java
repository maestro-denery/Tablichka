package dev.tablight.common.impl.llapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LLAPIPlugin extends JavaPlugin {
	private static final Logger logger = LoggerFactory.getLogger("LLAPI");
	@Override
	public void onEnable() {
		logger.info("Low-level API Enabled!");
	}

	@Override
	public void onDisable() {
		logger.info("Low-level API Disabled!");
	}
}
