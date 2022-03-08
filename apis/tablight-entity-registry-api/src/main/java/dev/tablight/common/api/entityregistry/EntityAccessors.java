package dev.tablight.common.api.entityregistry;

import org.bukkit.entity.Entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;

import dev.tablight.common.impl.entityregistry.EntityAccessorsImpl;

public interface EntityAccessors {
	static EntityAccessors getInstance() {
		return EntityAccessorsImpl.INSTANCE;
	}

	<T> EntityDataAccessor<T> defineID(Class<? extends Entity> clazz, EntityDataSerializer<T> dataSerializer);
}
