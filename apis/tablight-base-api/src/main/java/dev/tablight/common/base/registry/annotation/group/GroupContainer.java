package dev.tablight.common.base.registry.annotation.group;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import dev.tablight.common.base.registry.RegistryException;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.annotation.DataAddon;
import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.StoreLoadController;

/**
 * Container containing group tags and classes with their instances, it is needed only for {@link dev.tablight.common.base.registry.DataAddonBootstrap}
 */
public class GroupContainer {
	public final Map<String, Class<?>> implementations = new HashMap<>();
	public final Map<String, Class<? extends TypeRegistry>> typeRegistries = new HashMap<>();
	public final Map<String, Class<? extends TypeHolder>> holders = new HashMap<>();
	public final Map<String, Class<? extends StoreLoadController>> controllers = new HashMap<>();
	public final BiMap<Class<?>, Object> data = HashBiMap.create();

	public <T> void hold(T instance) {
		data.put(instance.getClass(), instance);
	}
	
	public <T> T getInstance(Class<T> clazz) {
		return (T) data.get(clazz);
	}

	public void registerTypeRegistry(Class<? extends TypeRegistry> clazz) {
		if (!clazz.isAnnotationPresent(Registry.class)) throw new RegistryException("Class doesn't have annotation");
		typeRegistries.put(clazz.getAnnotation(Registry.class).value(), clazz);
	}

	public void registerHolder(Class<? extends TypeHolder> clazz) {
		if (!clazz.isAnnotationPresent(Holder.class)) throw new RegistryException("Class doesn't have annotation");
		holders.put(clazz.getAnnotation(Holder.class).value(), clazz);
	}

	public void registerController(Class<? extends StoreLoadController> clazz) {
		if (!clazz.isAnnotationPresent(Controller.class)) throw new RegistryException("Class doesn't have annotation");
		controllers.put(clazz.getAnnotation(Controller.class).value(), clazz);
	}

	public void registerImplementation(Class<?> clazz) {
		implementations.put(clazz.getAnnotation(DataAddon.class).groupTag(), clazz);
	}
	
	public void clearAll() {
		implementations.clear();
		typeRegistries.clear();
		holders.clear();
		controllers.clear();
		data.clear();
	}
}
