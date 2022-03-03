package dev.tablight.entities.spawner;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;

import org.jetbrains.annotations.NotNull;

public class SomeCustomSpawner implements CustomSpawner {
	@Override
	public int tick(@NotNull ServerLevel world, boolean spawnMonsters, boolean spawnAnimals) {
		
		return 0;
	}
}
