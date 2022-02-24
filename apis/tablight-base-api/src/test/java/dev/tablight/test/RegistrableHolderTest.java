package dev.tablight.test;


import java.util.List;

import dev.tablight.common.base.global.GlobalRegistrableHolder;

import dev.tablight.common.base.global.GlobalTypeRegistry;

import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.RegistrableHolder;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.test.dummies.RegistrableDummy;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrableHolderTest {
	final RegistrableHolder holder = GlobalRegistrableHolder.getInstance();
	final TypeRegistry typeRegistry = GlobalTypeRegistry.getInstance();

	@Test
	void checkHold() {
		typeRegistry.register(RegistrableDummy.class);
		assertDoesNotThrow(() -> holder.hold(new RegistrableDummy()));
	}

	@Test
	void checkGetType() {
		typeRegistry.register(RegistrableDummy.class);
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		assertIterableEquals(List.of(dummy), holder.getHeld(RegistrableDummy.class));
	}

	@Test
	void checkGetID() {
		typeRegistry.register(RegistrableDummy.class);
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		assertIterableEquals(List.of(dummy), holder.getHeld("dummy"));
	}

	@Test
	void checkContains() {
		typeRegistry.register(RegistrableDummy.class);
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		assertTrue(holder.containsInstance(dummy));
	}
}
