package dev.tablight.entities;

import dev.tablight.entities.commands.CommandListener;

import dev.tablight.entities.commands.EntitiesCommand;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntitiesPlugin extends JavaPlugin {
	public static final Logger logger = LoggerFactory.getLogger("tablight-entities");

	@Override
	public void onEnable() {
		logger.info("Enabling Tablight Entities plugin...");
		Bukkit.getPluginManager().registerEvents(CommandListener.getInstance(), this);
		this.getCommand("entities").setExecutor(new EntitiesCommand());
	}

	@Override
	public void onDisable() {
		logger.info("Disabling Tablight Entities plugin...");
	}
}
