package dev.tablight.test.registries;

import dev.tablight.common.base.registry.DefaultTypeRegistry;
import dev.tablight.common.base.registry.annotation.group.Registry;

@Registry("dummyGroup")
public class DummyTypeRegistry extends DefaultTypeRegistry {
	private static DummyTypeRegistry instance;
	public DummyTypeRegistry getInstance() {
		if (instance == null) instance = new DummyTypeRegistry();
		return instance;
	}
}
