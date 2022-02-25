package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.DefaultTypeRegistry;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.holder.ConcurrentRegistrableHolder;
import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.DefaultStoreLoadController;
import dev.tablight.common.base.registry.storeload.StoreLoadController;
import dev.tablight.test.dummies.RegistrableDummy;
import dev.tablight.test.dummies.RegistrableDummyLookup;

public class StoreLoadControllerTest {
	TypeRegistry typeRegistry;
	TypeHolder holder;
	StoreLoadController controller;

	@BeforeEach
	void before() {
		typeRegistry = new DefaultTypeRegistry();
		holder = new ConcurrentRegistrableHolder();
		holder.addTypeRegistry(typeRegistry);
		typeRegistry.addRegistrableHolder(holder);
		controller = new DefaultStoreLoadController();
		controller.addRegistrableHolder(holder);
		typeRegistry.register(RegistrableDummy.class);
	}

	@AfterEach
	void after() {
		holder.clearHeld();
		typeRegistry.clearRegistry();
		typeRegistry = null;
		holder = null;
		controller = null;
	}

	@Test
	void checkStore() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newInstance(RegistrableDummy.class);
		controller.store(RegistrableDummy.class);
		assertEquals("store", dummy.getSomeString());
		assertEquals("store", dummy1.getSomeString());
	}

	@Test
	void checkLoad() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newInstance(RegistrableDummy.class);
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
		controller.lookupAndLoad(new RegistrableDummyLookup());
		RegistrableDummy dummy = holder.getHeld(RegistrableDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}

	@Test
	void checkLookupClass() {
		controller.lookup(RegistrableDummy.class);
		controller.load(RegistrableDummy.class);
		RegistrableDummy dummy = holder.getHeld(RegistrableDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}

	@Test
	void checkLookupAndLoadClass() {
		controller.lookupAndLoad(RegistrableDummy.class);
		RegistrableDummy dummy = holder.getHeld(RegistrableDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}
}
