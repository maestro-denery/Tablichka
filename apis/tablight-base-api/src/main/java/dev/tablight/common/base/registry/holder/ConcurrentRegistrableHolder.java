package dev.tablight.common.base.registry.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.RegistryException;
import dev.tablight.common.base.registry.TypeRegistry;

public class ConcurrentRegistrableHolder extends RegistrableHolder {
	protected final Collection<TypeRegistry> typeRegistries = new ArrayList<>();
	protected final Multimap<Class<? extends Registrable>, Registrable> instances =
			Multimaps.newMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);

	@Override
	public <T extends Registrable> void hold(T instance) {
		if (checkRegistered(instance.getClass())) {
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
	public <T extends Registrable> void release(Class<T> registrableType) {
		checkRegistered(registrableType);
		instances.removeAll(registrableType);
	}

	@Override
	public Collection<TypeRegistry> getTypeRegistries() {
		return typeRegistries;
	}

	@Override
	public void release(String identifier) {
		release(getClassByID(identifier));
	}

	@Override
	public void clearHeld() {
		instances.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Collection<T> getHeld(Class<T> registrableType) {
		return (Collection<T>) instances.get(registrableType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Collection<T> getHeld(String identifier) {
		return (Collection<T>) instances.get(getClassByID(identifier));
	}

	@Override
	public <T extends Registrable> boolean containsInstance(T registrable) {
		return instances.containsValue(registrable);
	}

	public Multimap<Class<? extends Registrable>, Registrable> getInternalMap() {
		return instances;
	}
	
	private <T extends Registrable> boolean checkRegistered(Class<T> tClass) {
		return typeRegistries.stream().anyMatch(typeRegistry -> typeRegistry.isRegistered(tClass));
	}
	
	@SuppressWarnings("all")
	private <T extends Registrable> Class<T> getClassByID(String id) {
		return (Class<T>) typeRegistries.stream()
				.map(typeRegistry -> typeRegistry.getRegistrableType(id)).findFirst().orElseThrow(() -> new RegistryException("There is no Registrables defined with given Id"));
	}
}
