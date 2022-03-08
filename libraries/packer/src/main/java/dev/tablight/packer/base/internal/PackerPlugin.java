package dev.tablight.packer.base.internal;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PackerPlugin extends JavaPlugin {
	public static final Logger logger = LoggerFactory.getLogger("Packer"); 
	
	@Override
	public void onEnable() {
		logger.info("Enabling Packer...");
	}

	@Override
	public void onDisable() {
		logger.info("Disabling Packer...");
	}
}
