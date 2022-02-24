package dev.tablight.test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.global.GlobalRegistrableHolder;
import dev.tablight.common.base.global.GlobalTypeRegistry;
import dev.tablight.common.base.registry.RegistrableHolder;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.test.dummies.RegistrableDummy;

public class RegistrableHolderTest {
	final RegistrableHolder holder = GlobalRegistrableHolder.getInstance();
	final TypeRegistry typeRegistry = GlobalTypeRegistry.getInstance();

	@BeforeEach
	void before() {
		typeRegistry.register(RegistrableDummy.class);
	}

	@AfterEach
	void after() {
		holder.clearHeld();
		typeRegistry.clearRegistry();
	}

	@Test
	void checkHold() {
		assertDoesNotThrow(() -> holder.hold(new RegistrableDummy()));
	}

	@Test
	void checkHoldEquality() {
		var dummy = new RegistrableDummy();
		holder.hold(dummy);
		assertEquals(dummy, holder.getHeld(RegistrableDummy.class).toArray()[0]);
	}

	@Test
	void checkGetType() {
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		assertIterableEquals(List.of(dummy), holder.getHeld(RegistrableDummy.class));
	}

	@Test
	void checkGetID() {
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		assertIterableEquals(List.of(dummy), holder.getHeld("dummy"));
	}

	@Test
	void checkContains() {
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		assertTrue(holder.containsInstance(dummy));
	}
}
