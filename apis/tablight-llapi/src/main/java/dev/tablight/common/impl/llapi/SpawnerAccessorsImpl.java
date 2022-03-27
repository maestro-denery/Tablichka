package dev.tablight.common.impl.llapi;

import com.google.common.collect.ImmutableList;

import dev.tablight.common.api.llapi.SpawnerAccessors;

import dev.tablight.common.api.llapi.VersionedClasses;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;

import org.bukkit.World;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.util.List;

public class SpawnerAccessorsImpl implements SpawnerAccessors {
	public static final SpawnerAccessorsImpl INSTANCE = new SpawnerAccessorsImpl();

	private static final VersionedClasses VERSIONED_CLASSES = VersionedClasses.getInstance();

	private static final VarHandle CUSTOM_SPAWNERS_LIST_VH;
	private static final VarHandle CRAFT_WORLD_SERVER_LEVEL;

	static {
		try {
			CRAFT_WORLD_SERVER_LEVEL = MethodHandles.privateLookupIn(VERSIONED_CLASSES.getCraftWorld(), MethodHandles.lookup())
					.findVarHandle(VERSIONED_CLASSES.getCraftWorld(), "world", ServerLevel.class);
			CUSTOM_SPAWNERS_LIST_VH = MethodHandles.privateLookupIn(ServerLevel.class, MethodHandles.lookup())
					.findVarHandle(
							ServerLevel.class,
							"Z",
							List.class
					);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new ExceptionInInitializerError("Can't initialize VarHandles!\n" + e.getMessage());
		}
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
