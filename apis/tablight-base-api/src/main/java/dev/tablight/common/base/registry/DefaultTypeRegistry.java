package dev.tablight.common.base.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;

public class DefaultTypeRegistry extends TypeRegistry {
	protected final Collection<RegistrableHolder> holders = new ArrayList<>();
	protected final BiMap<String, Class<? extends Registrable>> registryBiMap = HashBiMap.create();
	protected final Map<Class<? extends Registrable>, Pair<Supplier<? extends Registrable>, Supplier<? extends Registrable>>> storeLoadSuppliers = new HashMap<>();

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> Pair<Supplier<T>, Supplier<T>> getLazyStoreLoad(Class<T> registrableType) {
		return storeLoadSuppliers.get(registrableType).mapFirst(first -> (Supplier<T>) first).mapSecond(second -> (Supplier<T>) second);
	}

	@Override
	public void addRegistrableHolder(RegistrableHolder registrableHolder) {
		holders.add(registrableHolder);
	}

	@Override
	public void register(Class<? extends Registrable> registrableType) {
		try {
			Registrable registrable = registrableType.getDeclaredConstructor().newInstance();
			registryBiMap.put(registrable.identifier(), registrableType);
			storeLoadSuppliers.put(registrableType, Pair.of(registrable.lazystore(), registrable.lazyload()));
		} catch (Throwable e) {
			throw new RegistryException(registrableType, e);
		}
	}

	@Override
	public boolean isRegistered(Class<? extends Registrable> registrableType) {
		return registryBiMap.containsValue(registrableType);
	}

	@Override
	public void clearRegistry() {
		registryBiMap.clear();
	}

	@Override
	public <T extends Registrable> T newRegisteredInstance(Class<T> registrableType) {
		try {
			if (registryBiMap.containsValue(registrableType)) {
				var registrableInstance = registrableType.getDeclaredConstructor().newInstance();
				holders.forEach(holder -> holder.hold(registrableInstance));
				return registrableInstance;
			} else {
				throw new RegistryException(registrableType, "Registry doesn't contain this type, please register.");
			}
		} catch (Throwable e) {
			throw new RegistryException(registrableType, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Registrable> T newRegisteredInstance(String identifier) {
		Class<T> registrableType = (Class<T>) registryBiMap.get(identifier);
		return this.newRegisteredInstance(registrableType);
	}

	@Override
	public String getIdentifier(Class<? extends Registrable> registrableType) {
		if (registryBiMap.containsValue(registrableType)) {
			return registryBiMap.inverse().get(registrableType);
		} else {
			throw new RegistryException(registrableType);
		}
	}

	@Override
	public Class<? extends Registrable> getRegistrableType(String identifier) {
		if (registryBiMap.containsKey(identifier)) {
			return registryBiMap.get(identifier);
		} else {
			throw new RegistryException("There is no registered type under this Identifier");
		}
	}

	public BiMap<String, Class<? extends Registrable>> getInternalBiMap() {
		return registryBiMap;
	}
}
