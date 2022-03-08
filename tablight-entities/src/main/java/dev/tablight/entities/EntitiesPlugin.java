package dev.tablight.entities;

import com.google.common.collect.BiMap;

import com.google.common.collect.HashBiMap;

import dev.tablight.common.api.entityregistry.EntityAccessors;
import dev.tablight.common.api.llapi.SpawnerAccessors;
import dev.tablight.common.base.dataaddon.DataAddonBootstrap;
import dev.tablight.common.base.dataaddon.annotation.DataAddon;
import dev.tablight.common.base.dataaddon.GlobalGroupContainer;
import dev.tablight.entities.commands.EntitiesCommand;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EntitiesPlugin extends JavaPlugin {
	public static final Logger logger = LoggerFactory.getLogger("tablight-entities");
	private static final DataAddonBootstrap bootstrap = new DataAddonBootstrap();
	private static final BiMap<Class<?>, EntityDataAccessor<CompoundTag>> accessors = HashBiMap.create();

	public static DataAddonBootstrap getBootstrap() {
		return bootstrap;
	}

	public static BiMap<Class<?>, EntityDataAccessor<CompoundTag>> getAccessors() {
		return accessors;
	}

	@Override
	public void onEnable() {
		logger.info("Enabling Tablight Entities plugin...");

		final SpawnerAccessors spawnerAccessors = SpawnerAccessors.getInstance();
		
		bootstrap.setContainer(GlobalGroupContainer.getInstance());
		bootstrap.bootstrapRegistries("dev.tablight.entities.registries");
		bootstrap.bootstrapDataAddons("dev.tablight.entities.impls", clazz -> {
			
		});
		//spawnerAccessors.addCustomSpawner(Bukkit.getWorld("world"), new LochnessMonsterSpawner());

		this.getCommand("tl-entities").setExecutor(new EntitiesCommand());
	}

	@Override
	public void onDisable() {
		logger.info("Disabling Tablight Entities plugin...");
	}
}
