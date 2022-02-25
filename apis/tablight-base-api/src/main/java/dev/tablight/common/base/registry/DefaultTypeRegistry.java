package dev.tablight.common.base.registry;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import dev.tablight.common.base.registry.annotation.DataAddon;
import dev.tablight.common.base.registry.holder.TypeHolder;

import static dev.tablight.common.base.registry.annotation.AnnotationUtil.checkAnnotation;

public class DefaultTypeRegistry extends TypeRegistry {
	protected final Collection<TypeHolder> holders = new ArrayList<>();
	protected final BiMap<String, Class<?>> registryBiMap = HashBiMap.create();

	@Override
	public void addRegistrableHolder(TypeHolder registrableHolder) {
		holders.add(registrableHolder);
	}

	@Override
	public void register(Class<?> registrableType) {
		checkAnnotation(registrableType);
		try {
			registryBiMap.put(registrableType.getAnnotation(DataAddon.class).identifier(), registrableType);
		} catch (Throwable e) {
			throw new RegistryException(registrableType, e);
		}
	}

	@Override
	public boolean isRegistered(Class<?> registrableType) {
		checkAnnotation(registrableType);
		return registryBiMap.containsValue(registrableType);
	}

	@Override
	public void clearRegistry() {
		registryBiMap.clear();
	}

	@Override
	public Collection<TypeHolder> getRegistrableHolders() {
		return holders;
	}

	@Override
	public <T> T newInstance(Class<T> registrableType) {
		checkContains(registrableType);
		try {
			var registrableInstance = registrableType.getDeclaredConstructor().newInstance();
			holders.forEach(holder -> holder.hold(registrableInstance));
			return registrableInstance;
		} catch (Throwable e) {
			throw new RegistryException(registrableType, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T newInstance(String identifier) {
		Class<T> registrableType = (Class<T>) registryBiMap.get(identifier);
		return this.newInstance(registrableType);
	}

	@Override
	public String getIdentifier(Class<?> registrableType) {
		checkContains(registrableType);
		return registryBiMap.inverse().get(registrableType);
	}

	@Override
	public Class<?> getRegistrableType(String identifier) {
		if (registryBiMap.containsKey(identifier)) {
			return registryBiMap.get(identifier);
		} else {
			throw new RegistryException("There is no registered type under this Identifier");
		}
	}

	public BiMap<String, Class<?>> getInternalBiMap() {
		return registryBiMap;
	}
	
	private <T> void checkContains(Class<T> clazz) {
		if (!registryBiMap.containsValue(clazz)) throw new RegistryException(clazz, "Registry doesn't contain this type, please register.");
	}
}
