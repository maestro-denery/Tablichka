package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.*;

import dev.tablight.common.base.global.GlobalTypeRegistry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.test.dummies.RegistrableDummy;

public class TypeRegistryTest {
	final TypeRegistry registry = GlobalTypeRegistry.getInstance();
	
	@BeforeEach
	void before() {
		registry.register(RegistrableDummy.class);
	}

	@AfterEach
	void after() {
		registry.clearRegistry();
	}

	@Test
	void checkIsRegistered() {
		assertTrue(registry.isRegistered(RegistrableDummy.class));
	}

	@Test
	void checkNewInstanceClass() {
		assertNotNull(registry.newRegisteredInstance(RegistrableDummy.class));
	}

	@Test
	void checkNewInstanceID() {
		assertNotNull(registry.newRegisteredInstance("dummy"));
	}
	
	@Test
	void checkGetIdentifier() {
		assertEquals("dummy", registry.getIdentifier(RegistrableDummy.class));
	}
	
	@Test
	void checkGetClass() {
		assertEquals(RegistrableDummy.class, registry.getRegistrableType("dummy"));
	}
	
	@Test
	void checkLazyStoreLoad() {
		assertEquals("store", registry.getLazyStoreLoad(RegistrableDummy.class).getFirst().get().getSomeString());
	}
}
