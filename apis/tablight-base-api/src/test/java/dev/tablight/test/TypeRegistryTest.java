package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.*;

import dev.tablight.common.base.global.GlobalTypeRegistry;

import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.test.dummies.RegistrableDummy;

public class TypeRegistryTest {
	final TypeRegistry registry = GlobalTypeRegistry.getInstance();

	@Test
	void checkIsRegistered() {
		registry.register(RegistrableDummy.class);
		assertTrue(registry.isRegistered(RegistrableDummy.class));
	}

	@Test
	void checkNewInstanceClass() {
		registry.register(RegistrableDummy.class);
		assertNotNull(registry.newRegisteredInstance(RegistrableDummy.class));
	}

	@Test
	void checkNewInstanceID() {
		registry.register(RegistrableDummy.class);
		assertNotNull(registry.newRegisteredInstance("dummy"));
	}
	
	@Test
	void checkGetIdentifier() {
		registry.register(RegistrableDummy.class);
		assertEquals("dummy", registry.getIdentifier(RegistrableDummy.class));
	}
	
	@Test
	void checkGetClass() {
		registry.register(RegistrableDummy.class);
		assertEquals(RegistrableDummy.class, registry.getRegistrableType("dummy"));
	}
	
	@Test
	void checkLazyStoreLoad() {
		registry.register(RegistrableDummy.class);
		assertEquals("store", registry.getLazyStoreLoad(RegistrableDummy.class).getFirst().get().getSomeString());
	}
}
