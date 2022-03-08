package dev.tablight.entities.registries;

import com.google.common.collect.BiMap;

import dev.tablight.common.api.entityregistry.EntityAccessors;
import dev.tablight.common.base.dataaddon.DataAddonBootstrap;
import dev.tablight.common.base.dataaddon.annotation.DataAddon;
import dev.tablight.common.base.dataaddon.typeregistry.DefaultTypeRegistry;
import dev.tablight.common.base.dataaddon.annotation.group.Registry;

import dev.tablight.entities.EntitiesPlugin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;

import org.bukkit.entity.Entity;

@Registry("entities-group")
public class EntityTypeRegistry extends DefaultTypeRegistry {
	@Override
	public void register(Class<?> registrableType) {
		DataAddonBootstrap bootstrap = EntitiesPlugin.getBootstrap();
		BiMap<Class<?>, EntityDataAccessor<CompoundTag>> accessors = EntitiesPlugin.getAccessors();

		final EntityAccessors entityAccessors = EntityAccessors.getInstance();
		Class<? extends Entity> nativeClass = (Class<? extends Entity>) bootstrap.getDataAddonInfo(registrableType).nativeClass();
		accessors.put(nativeClass, entityAccessors.defineID(
				nativeClass,
				EntityDataSerializers.COMPOUND_TAG
		));
		super.register(registrableType);
	}
}
