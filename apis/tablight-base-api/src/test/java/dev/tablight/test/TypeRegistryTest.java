package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.*;

import dev.tablight.common.base.global.GlobalTypeRegistry;

import dev.tablight.common.base.registry.DefaultTypeRegistry;

import dev.tablight.common.base.registry.holder.ConcurrentRegistrableHolder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.test.dummies.RegistrableDummy;

public class TypeRegistryTest {
	TypeRegistry registry;
	
	@BeforeEach
	void before() {
		registry = new DefaultTypeRegistry();
		ConcurrentRegistrableHolder concurrentRegistrableHolder = new ConcurrentRegistrableHolder();
		concurrentRegistrableHolder.addTypeRegistry(registry);
		registry.addRegistrableHolder(concurrentRegistrableHolder);
		registry.register(RegistrableDummy.class);
	}

	@AfterEach
	void after() {
		registry.clearRegistry();
		registry = null;
	}

	@Test
	void checkIsRegistered() {
		assertTrue(registry.isRegistered(RegistrableDummy.class));
	}

	@Test
	void checkNewInstanceClass() {
		assertNotNull(registry.newInstance(RegistrableDummy.class));
	}

	@Test
	void checkNewInstanceID() {
		assertNotNull(registry.newInstance("dummy"));
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
