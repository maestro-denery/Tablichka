package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.tablight.common.base.global.GlobalStoreLoadController;

import org.junit.jupiter.api.Test;

import dev.tablight.common.base.global.GlobalTypeRegistry;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.storeload.StoreLoadController;
import dev.tablight.test.dummies.RegistrableDummy;

public class StoreLoadControllerTest {
	final TypeRegistry typeRegistry = GlobalTypeRegistry.getInstance();
	final StoreLoadController controller = GlobalStoreLoadController.getInstance();

	@Test
	void checkStore() {
		typeRegistry.register(RegistrableDummy.class);
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		controller.store(RegistrableDummy.class);
		assertEquals("store", dummy.getSomeString());
		assertEquals("store", dummy1.getSomeString());
	}
}
