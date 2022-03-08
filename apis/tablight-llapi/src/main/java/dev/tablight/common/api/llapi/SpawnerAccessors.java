package dev.tablight.common.api.llapi;

import dev.tablight.common.impl.llapi.SpawnerAccessorsImpl;

import net.minecraft.world.level.CustomSpawner;

import org.bukkit.World;

public interface SpawnerAccessors {
	static SpawnerAccessors getInstance() {
		return SpawnerAccessorsImpl.INSTANCE;
	}
	
	void addCustomSpawner(World world, CustomSpawner customSpawner);
}
