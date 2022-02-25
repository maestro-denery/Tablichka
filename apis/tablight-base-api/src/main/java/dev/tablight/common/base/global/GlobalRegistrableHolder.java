package dev.tablight.common.base.global;

import dev.tablight.common.base.registry.holder.ConcurrentRegistrableHolder;

public final class GlobalRegistrableHolder extends ConcurrentRegistrableHolder {
	private static GlobalRegistrableHolder instance;
	public static GlobalRegistrableHolder getInstance() {
		if (instance == null) {
			instance = new GlobalRegistrableHolder();
			instance.typeRegistries.add(GlobalTypeRegistry.getInstance());
		}
		return instance;
	}
}
