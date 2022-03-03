package dev.tablight.entities.lookups;

import dev.tablight.common.base.dataaddon.storeload.StoreLoadLookup;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import org.bukkit.Bukkit;

import java.util.stream.Stream;

public abstract class LivingEntityLookup<T> implements StoreLoadLookup<T, LivingEntity> {
	protected Stream<Entity> allEntitiesStream = Bukkit.getServer().getWorlds().stream()
			.flatMap(world -> world.getEntities().stream())
			.map(bukkitEntity -> ((Entity) bukkitEntity));
}
