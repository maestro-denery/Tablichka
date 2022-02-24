package dev.tablight.common.base.global;

import dev.tablight.common.base.registry.RegistrableHolder;
import dev.tablight.common.base.registry.RegistryException;
import dev.tablight.common.base.registry.DefaultTypeRegistry;

public final class GlobalTypeRegistry extends DefaultTypeRegistry {
	private static GlobalTypeRegistry instance;
	public static GlobalTypeRegistry getInstance() {
		if (instance == null) {
			instance = new GlobalTypeRegistry();
			instance.holders.add(GlobalRegistrableHolder.getInstance());
		}
		return instance;
	}

	@Override
	public void addRegistrableHolder(RegistrableHolder registrableHolder) {
		throw new RegistryException("Global type registry has only one holder!");
	}
}
