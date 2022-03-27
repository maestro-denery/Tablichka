package dev.tablight.entities.lookups;

import java.util.stream.Stream;

import dev.tablight.dataaddon.mark.Mark;

import net.minecraft.world.entity.LivingEntity;

import org.bukkit.Bukkit;

import net.minecraft.world.entity.Entity;

import dev.tablight.dataaddon.DataAddonBootstrap;
import dev.tablight.dataaddon.storeload.StoreLoadLookup;
import dev.tablight.entities.EntitiesPlugin;

public abstract class LivingEntityLookup<T, N extends LivingEntity> implements StoreLoadLookup<T, N> {
	protected DataAddonBootstrap bootstrap = EntitiesPlugin.getBootstrap();

	protected Stream<N> allEntitiesStream = Bukkit.getServer().getWorlds().stream()
			.flatMap(world -> world.getEntities().stream())
			.map(bukkitEntity -> ((N) bukkitEntity));

	public boolean isCustom(Entity entity, Class<? extends T> clazz) {
		return entity.getEntityData().get(EntitiesPlugin.getAccessors().get(entity.getClass()))
				.get("tablight-entity").getAsString().equals(bootstrap.getDataAddonInfo(clazz).identifier());
	}
}
