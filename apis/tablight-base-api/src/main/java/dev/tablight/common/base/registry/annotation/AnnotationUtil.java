package dev.tablight.common.base.registry.annotation;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import dev.tablight.common.base.registry.RegistryException;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.annotation.group.Controller;
import dev.tablight.common.base.registry.annotation.group.GroupContainer;
import dev.tablight.common.base.registry.annotation.group.Holder;
import dev.tablight.common.base.registry.annotation.group.Registry;
import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.StoreLoadController;

/**
 * Internal Utilities for handling annotations, regular user shouldn't touch it.
 */
public final class AnnotationUtil {
	public static void connectGroupsInRepoByTag(String tag, GroupContainer repository) {
		List<? extends TypeRegistry> registries = repository.typeRegistries.values().stream()
				.filter(clazz -> clazz.getAnnotation(Registry.class).value().equals(tag))
				.map(clazz -> {
					try {
						TypeRegistry registry = clazz.getDeclaredConstructor().newInstance();
						repository.hold(registry);
						return registry;
					} catch (ReflectiveOperationException e) {
						throw new RegistryException("Can't create new instance, check class requirements");
					}
				}).toList();
		List<? extends TypeHolder> holders = repository.holders.values().stream()
				.filter(clazz -> clazz.getAnnotation(Holder.class).value().equals(tag))
				.map(clazz -> {
					try {
						TypeHolder typeHolder = clazz.getDeclaredConstructor().newInstance();
						repository.hold(typeHolder);
						return typeHolder;
					} catch (ReflectiveOperationException e) {
						throw new RegistryException("Can't create new instance, check class requirements");
					}
				}).toList();
		List<? extends StoreLoadController> controllers = repository.controllers.values().stream()
				.filter(clazz -> clazz.getAnnotation(Controller.class).value().equals(tag))
				.map(clazz -> {
					try {
						StoreLoadController controller = clazz.getDeclaredConstructor().newInstance();
						repository.hold(controller);
						return controller;
					} catch (ReflectiveOperationException e) {
						throw new RegistryException("Can't create new instance, check class requirements");
					}
				}).toList();
		registries.forEach(typeRegistry -> holders.forEach(typeRegistry::addRegistrableHolder));
		holders.forEach(holder -> registries.forEach(holder::addTypeRegistry));
		controllers.forEach(controller -> holders.forEach(controller::addRegistrableHolder));
	}

	public static void connectImplByTag(String tag, GroupContainer repository) {
		repository.data.values().stream().filter(obj -> obj.getClass().isAnnotationPresent(Registry.class) && obj instanceof TypeRegistry)
				.forEach(obj -> ((TypeRegistry) obj).register(repository.implementations.get(tag)));
	}

	public static void checkAnnotation(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(DataAddon.class)) throw new RegistryException("Type: " + clazz.getSimpleName() + " has no annotation");
	}

	public static void invokeLoad(Object held) {
		checkAnnotation(held.getClass());
		Arrays.stream(held.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Load.class)).forEach(method -> {
			try {
				method.invoke(held);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RegistryException("Check if your load method has no parameters.");
			}
		});
	}

	public static void invokeStore(Object held) {
		checkAnnotation(held.getClass());
		Arrays.stream(held.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Store.class)).forEach(method -> {
			try {
				method.invoke(held);
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RegistryException("Check if your load method has no parameters.");
			}
		});
	}
}
