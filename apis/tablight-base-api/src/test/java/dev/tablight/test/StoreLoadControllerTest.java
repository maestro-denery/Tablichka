package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.tablight.common.base.global.GlobalRegistrableHolder;
import dev.tablight.common.base.global.GlobalStoreLoadController;

import dev.tablight.common.base.registry.RegistrableHolder;

import dev.tablight.test.dummies.RegistrableDummyLookup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.global.GlobalTypeRegistry;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.storeload.StoreLoadController;
import dev.tablight.test.dummies.RegistrableDummy;

public class StoreLoadControllerTest {
	final TypeRegistry typeRegistry = GlobalTypeRegistry.getInstance();
	final RegistrableHolder holder = GlobalRegistrableHolder.getInstance();
	final StoreLoadController controller = GlobalStoreLoadController.getInstance();

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
	void checkStore() {
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		controller.store(RegistrableDummy.class);
		assertEquals("store", dummy.getSomeString());
		assertEquals("store", dummy1.getSomeString());
	}

	@Test
	void checkLoad() {
		var dummy = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newRegisteredInstance(RegistrableDummy.class);
		controller.load(RegistrableDummy.class);
		assertEquals("load", dummy.getSomeString());
		assertEquals("load", dummy1.getSomeString());
	}

	@Test
	void checkLookup() {
		controller.lookup(new RegistrableDummyLookup());
		controller.load(RegistrableDummy.class);
		RegistrableDummy dummy = holder.getHeld(RegistrableDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}

	@Test
	void checkLookupAndLoad() {
		controller.lookupAndLoad(RegistrableDummy.class, new RegistrableDummyLookup());
		RegistrableDummy dummy = holder.getHeld(RegistrableDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}
}
