package dev.tablight.common.impl.entityregistry;

import org.bukkit.entity.Entity;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;

import dev.tablight.common.api.entityregistry.EntityAccessors;

public class EntityAccessorsImpl implements EntityAccessors {
	public static final EntityAccessorsImpl INSTANCE = new EntityAccessorsImpl();

	@Override
	public <T> net.minecraft.network.syncher.EntityDataAccessor<T> defineID(Class<? extends Entity> clazz, EntityDataSerializer<T> dataSerializer) {
		return SynchedEntityData.defineId(clazz.asSubclass(net.minecraft.world.entity.Entity.class), dataSerializer);
	}
}
