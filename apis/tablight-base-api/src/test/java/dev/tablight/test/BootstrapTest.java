package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.tablight.test.registries.DummyTypeRegistry;

import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.DataAddonBootstrap;
import dev.tablight.common.base.registry.annotation.group.GroupContainer;

public class BootstrapTest {
	@Test
	void checkBootstrapRegistries() {
		var repo = new GroupContainer();
		DataAddonBootstrap dataAddonBootstrap = new DataAddonBootstrap();
		dataAddonBootstrap.setRepository(repo);
		assertDoesNotThrow(() -> dataAddonBootstrap.bootstrapRegistries("dev.tablight.test.registries"));
	}

	@Test
	void checkBootstrapInstances() {
		var repo = new GroupContainer();
		DataAddonBootstrap dataAddonBootstrap = new DataAddonBootstrap();
		dataAddonBootstrap.setRepository(repo);
		dataAddonBootstrap.bootstrapRegistries("dev.tablight.test.registries");
		dataAddonBootstrap.bootstrapImplementations("dev.tablight.test.dummies");
		assertNotNull(repo.getInstance(DummyTypeRegistry.class));
	}
}
