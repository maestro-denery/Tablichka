package dev.tablight.common.impl.entityregistry;

import com.google.common.collect.ImmutableList;

import dev.tablight.common.api.llapi.CommonAccessor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;

import org.bukkit.World;
import org.bukkit.entity.Entity;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;

import dev.tablight.common.api.entityregistry.EntityAccessors;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.List;

public class EntityAccessorsImpl implements EntityAccessors {
	public static final EntityAccessorsImpl INSTANCE = new EntityAccessorsImpl();

	private static final CommonAccessor commonAccessor = CommonAccessor.create("v1_17_R1");
	
	private static final VarHandle CUSTOM_SPAWNERS_LIST_VH;
	private static final VarHandle CRAFT_WORLD_SERVER_LEVEL;

	static {
		try {
			CUSTOM_SPAWNERS_LIST_VH = MethodHandles.privateLookupIn(ServerLevel.class, MethodHandles.lookup())
					.findVarHandle(ServerLevel.class, "P", List.class);
			CRAFT_WORLD_SERVER_LEVEL = MethodHandles.privateLookupIn(commonAccessor.getCraftWorldClass(), MethodHandles.lookup())
					.findVarHandle(commonAccessor.getCraftWorldClass(), "world", ServerLevel.class);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new ExceptionInInitializerError("Can't initialize VarHandles!");
		}
	}

	@Override
	public <T> net.minecraft.network.syncher.EntityDataAccessor<T> defineID(Class<? extends Entity> clazz, EntityDataSerializer<T> dataSerializer) {
		return SynchedEntityData.defineId(clazz.asSubclass(net.minecraft.world.entity.Entity.class), dataSerializer);
	}

	@Override
	public void addCustomSpawner(World world, CustomSpawner customSpawner) {
		ServerLevel serverLevel = (ServerLevel) CRAFT_WORLD_SERVER_LEVEL.get(world);
		try {
			Field customSpawnersField = ServerLevel.class.getDeclaredField("P");
			customSpawnersField.setAccessible(true);
			customSpawnersField.set(serverLevel, ImmutableList.builder()
					.addAll((List<CustomSpawner>) CUSTOM_SPAWNERS_LIST_VH.get(serverLevel))
					.add(customSpawner)
					.build());
		} catch (IllegalAccessException | NoSuchFieldException e) {
			throw new IllegalStateException("Cannot set custom spawner to a field!");
		}
	}
}
