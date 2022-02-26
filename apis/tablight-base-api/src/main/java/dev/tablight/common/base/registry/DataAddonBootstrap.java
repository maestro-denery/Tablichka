package dev.tablight.common.base.registry;

import java.io.IOException;
import java.util.List;

import com.google.common.reflect.ClassPath;

import dev.tablight.common.base.registry.annotation.AnnotationUtil;
import dev.tablight.common.base.registry.annotation.DataAddon;
import dev.tablight.common.base.registry.annotation.group.Controller;
import dev.tablight.common.base.registry.annotation.group.GroupContainer;
import dev.tablight.common.base.registry.annotation.group.Holder;
import dev.tablight.common.base.registry.annotation.group.Registry;
import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.StoreLoadController;

@SuppressWarnings("UnstableApiUsage")
public final class DataAddonBootstrap {
	public GroupContainer container;

	public void setContainer(GroupContainer container) {
		this.container = container;
	}

	public GroupContainer getContainer() {
		return container;
	}

	/**
	 * Configures {@link TypeRegistry}, {@link TypeHolder}, {@link StoreLoadController} and other infrastructure in the package.
	 * @param packageName package containing registries.
	 */
	public void bootstrapRegistries(String packageName) {
		try {
			List<? extends Class<?>> classesInPackage = ClassPath.from(ClassLoader.getSystemClassLoader())
					.getAllClasses()
					.stream()
					.filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName))
					.map(ClassPath.ClassInfo::load)
					.toList();
			
			classesInPackage.stream()
					.filter(clazz -> clazz.isAnnotationPresent(Registry.class))
					.forEach(clazz -> container.registerTypeRegistry((Class<? extends TypeRegistry>) clazz));

			classesInPackage.stream()
					.filter(clazz -> clazz.isAnnotationPresent(Holder.class))
					.forEach(clazz -> container.registerHolder((Class<? extends TypeHolder>) clazz));

			classesInPackage.stream()
					.filter(clazz -> clazz.isAnnotationPresent(Controller.class))
					.forEach(clazz -> container.registerController((Class<? extends StoreLoadController>) clazz));
			
			classesInPackage.stream().forEach(clazz -> {
				if (clazz.isAnnotationPresent(Registry.class)) 
					AnnotationUtil.connectGroupsInRepoByTag(clazz.getDeclaredAnnotation(Registry.class).value(), container);
				else if (clazz.isAnnotationPresent(Holder.class))
					AnnotationUtil.connectGroupsInRepoByTag(clazz.getDeclaredAnnotation(Holder.class).value(), container);
				else if (clazz.isAnnotationPresent(Controller.class))
					AnnotationUtil.connectGroupsInRepoByTag(clazz.getDeclaredAnnotation(Controller.class).value(), container);
			});
		} catch (IOException e) {
			throw new RuntimeException("Something went wrong while bootstrapping Registries.");
		}
	}

	/**
	 * Configures {@link DataAddon} implementation and connects it with already existing infrastructure
	 * configured with {@link #bootstrapRegistries(String)} and contained in {@link #container}
	 * @param packageName package containing DataAddons.
	 */
	public void bootstrapImplementations(String packageName) {
		try {
			List<? extends Class<?>> implClasses = ClassPath.from(ClassLoader.getSystemClassLoader())
					.getAllClasses()
					.stream()
					.filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName))
					.map(ClassPath.ClassInfo::load)
					.filter(clazz -> clazz.isAnnotationPresent(DataAddon.class))
					.toList();
			
			implClasses.forEach(container::registerImplementation);
			implClasses.forEach(clazz -> AnnotationUtil.connectImplByTag(clazz.getAnnotation(DataAddon.class).groupTag(), container));
		} catch (IOException e) {
			throw new RuntimeException("Something went wrong while bootstrapping Registries.");
		}
	}

	/**
	 * @return registry instance contained in {@link #container} by its class.
	 */
	public <T extends TypeRegistry> T getTypeRegistry(Class<T> clazz) {
		return (T) container.data.get(clazz);
	}

	/**
	 * @return registry instance contained in {@link #container} by its class.
	 */
	public <T extends TypeHolder> T getTypeHolder(Class<T> clazz) {
		return (T) container.data.get(clazz);
	}

	/**
	 * @return registry instance contained in {@link #container} by its class.
	 */
	public <T extends StoreLoadController> T getStoreLoadController(Class<T> clazz) {
		return (T) container.data.get(clazz);
	}
}
