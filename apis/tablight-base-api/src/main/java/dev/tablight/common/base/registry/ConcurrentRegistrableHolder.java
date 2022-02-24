package dev.tablight.common.base.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public abstract class ConcurrentRegistrableHolder extends RegistrableHolder {
	protected final Collection<TypeRegistry> typeRegistries = new ArrayList<>();
	protected final Multimap<Class<? extends Registrable>, Registrable> instances =
			Multimaps.newMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);

	@Override
	public <T extends Registrable> void hold(T instance) {
		if (typeRegistries.stream().anyMatch(typeRegistry -> typeRegistry.isRegistered(instance.getClass()))) {
			instances.put(instance.getClass(), instance);
		} else {
			throw new RegistryException(instance.getClass(), "Can't hold registrable because it isn't registered");
		}
	}

	@Override
	public void addTypeRegistry(TypeRegistry typeRegistry) {
		typeRegistries.add(typeRegistry);
	}

	@Override
	public <T extends Registrable> void release(T instance) {
		instances.remove(instance.getClass(), instance);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Collection<T> getHeld(Class<T> registrableType) {
		return (Collection<T>) instances.get(registrableType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Collection<T> getHeld(String identifier) {
		return (Collection<T>) instances.get(typeRegistries.stream()
				.map(typeRegistry -> typeRegistry.getRegistrableType(identifier)).findFirst().orElseThrow(() -> new RegistryException("There is no Registrables defined with given Id")));
	}

	@Override
	public <T extends Registrable> boolean containsInstance(T registrable) {
		return instances.containsValue(registrable);
	}

	public Multimap<Class<? extends Registrable>, Registrable> getInternalMap() {
		return instances;
	}
}
