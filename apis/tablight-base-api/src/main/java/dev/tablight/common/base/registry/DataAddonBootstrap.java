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
	public GroupContainer repository;

	public void setRepository(GroupContainer repository) {
		this.repository = repository;
	}

	public GroupContainer getRepository() {
		return repository;
	}

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
					.forEach(clazz -> repository.registerTypeRegistry((Class<? extends TypeRegistry>) clazz));

			classesInPackage.stream()
					.filter(clazz -> clazz.isAnnotationPresent(Holder.class))
					.forEach(clazz -> repository.registerHolder((Class<? extends TypeHolder>) clazz));

			classesInPackage.stream()
					.filter(clazz -> clazz.isAnnotationPresent(Controller.class))
					.forEach(clazz -> repository.registerController((Class<? extends StoreLoadController>) clazz));
			
			classesInPackage.stream().forEach(clazz -> {
				if (clazz.isAnnotationPresent(Registry.class)) 
					AnnotationUtil.connectGroupsInRepoByTag(clazz.getDeclaredAnnotation(Registry.class).value(), repository);
				else if (clazz.isAnnotationPresent(Holder.class))
					AnnotationUtil.connectGroupsInRepoByTag(clazz.getDeclaredAnnotation(Holder.class).value(), repository);
				else if (clazz.isAnnotationPresent(Controller.class))
					AnnotationUtil.connectGroupsInRepoByTag(clazz.getDeclaredAnnotation(Controller.class).value(), repository);
			});
		} catch (IOException e) {
			throw new RuntimeException("Something went wrong while bootstrapping Registries.");
		}
	}

	public void bootstrapImplementations(String packageName) {
		try {
			List<? extends Class<?>> implClasses = ClassPath.from(ClassLoader.getSystemClassLoader())
					.getAllClasses()
					.stream()
					.filter(clazz -> clazz.getPackageName().equalsIgnoreCase(packageName))
					.map(ClassPath.ClassInfo::load)
					.filter(clazz -> clazz.isAnnotationPresent(DataAddon.class))
					.toList();
			
			implClasses.forEach(repository::registerImplementation);
			implClasses.forEach(clazz -> AnnotationUtil.connectImplByTag(clazz.getAnnotation(DataAddon.class).groupTag(), repository));
		} catch (IOException e) {
			throw new RuntimeException("Something went wrong while bootstrapping Registries.");
		}
	}

	public TypeRegistry getTypeRegistry() {
		if (repository.typeRegistries.values().size() == 1) {
			return repository.data.values().stream().filter(obj -> obj instanceof TypeRegistry).map(obj -> ((TypeRegistry) obj)).findFirst()
					.orElseThrow(() -> new RuntimeException("Something really went wrong"));
		} else {
			throw new RegistryException("You have multiple holders registered!");
		}
	}
	
	public TypeHolder getTypeHolder() {
		if (repository.holders.values().size() == 1) {
			return repository.data.values().stream().filter(obj -> obj instanceof TypeHolder).map(obj -> ((TypeHolder) obj)).findFirst()
					.orElseThrow(() -> new RuntimeException("Something really went wrong"));
		} else {
			throw new RegistryException("You have multiple holders registered!");
		}
	}

	public StoreLoadController getStoreLoadController() {
		if (repository.controllers.values().size() == 1) {
			return repository.data.values().stream().filter(obj -> obj instanceof StoreLoadController).map(obj -> ((StoreLoadController) obj)).findFirst()
					.orElseThrow(() -> new RuntimeException("Something really went wrong"));
		} else {
			throw new RegistryException("You have multiple holders registered!");
		}
	}
}
