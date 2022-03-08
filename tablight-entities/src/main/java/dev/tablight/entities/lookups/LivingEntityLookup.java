package dev.tablight.entities.lookups;

import java.util.stream.Stream;

import org.bukkit.Bukkit;

import net.minecraft.world.entity.Entity;

import dev.tablight.dataaddon.DataAddonBootstrap;
import dev.tablight.dataaddon.storeload.StoreLoadLookup;
import dev.tablight.entities.EntitiesPlugin;

public abstract class LivingEntityLookup<T, N> implements StoreLoadLookup<T, N> {
	protected DataAddonBootstrap bootstrap = EntitiesPlugin.getBootstrap();

	protected Stream<Entity> allEntitiesStream = Bukkit.getServer().getWorlds().stream()
			.flatMap(world -> world.getEntities().stream())
			.map(bukkitEntity -> ((Entity) bukkitEntity));

	public boolean isCustom(Entity entity, Class<T> clazz) {
		return entity.getEntityData().get(EntitiesPlugin.getAccessors().get(entity.getClass()))
				.get("tablight-entity").getAsString().equals(bootstrap.getDataAddonInfo(clazz).identifier());
	}
}
