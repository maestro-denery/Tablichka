package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.DataAddonBootstrap;
import dev.tablight.common.base.registry.annotation.group.GroupContainer;
import dev.tablight.test.registries.DummyController;
import dev.tablight.test.registries.DummyHolder;
import dev.tablight.test.registries.DummyTypeRegistry;

public class BootstrapTest {
	GroupContainer container;
	DataAddonBootstrap bootstrap = new DataAddonBootstrap();

	@BeforeEach
	void before() {
		container = new GroupContainer();
		bootstrap.setContainer(container);
		bootstrap.bootstrapRegistries("dev.tablight.test.registries");
		bootstrap.bootstrapImplementations("dev.tablight.test.dummies");
	}

	@AfterEach
	void after() {
		container = null;
		bootstrap = null;
	}

	@Test
	void checkTypeRegistryInstance() {
		assertNotNull(bootstrap.getTypeRegistry(DummyTypeRegistry.class));
	}

	@Test
	void checkTypeHolderInstance() {
		assertNotNull(bootstrap.getTypeHolder(DummyHolder.class));
	}

	@Test
	void checkStoreLoadControllerInstance() {
		assertNotNull(bootstrap.getStoreLoadController(DummyController.class));
	}
}
