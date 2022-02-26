package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.DataAddonBootstrap;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.annotation.group.GroupContainer;
import dev.tablight.test.dummies.RegistrableDummy;
import dev.tablight.test.registries.DummyTypeRegistry;

public class TypeRegistryTest {
	DataAddonBootstrap dataAddonBootstrap = new DataAddonBootstrap();
	TypeRegistry registry;
	
	@BeforeEach
	void before() {
		dataAddonBootstrap.setContainer(new GroupContainer());
		dataAddonBootstrap.bootstrapRegistries("dev.tablight.test.registries");
		dataAddonBootstrap.bootstrapImplementations("dev.tablight.test.dummies");
		registry = dataAddonBootstrap.getTypeRegistry(DummyTypeRegistry.class);
	}

	@AfterEach
	void after() {
		registry.clearRegistry();
		dataAddonBootstrap.getContainer().clearAll();
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
}
