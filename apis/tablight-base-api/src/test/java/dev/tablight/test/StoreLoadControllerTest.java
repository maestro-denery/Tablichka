package dev.tablight.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.dataaddon.DefaultTypeRegistry;
import dev.tablight.common.base.dataaddon.TypeRegistry;
import dev.tablight.common.base.dataaddon.holder.ConcurrentTypeHolder;
import dev.tablight.common.base.dataaddon.holder.TypeHolder;
import dev.tablight.common.base.dataaddon.storeload.DefaultStoreLoadController;
import dev.tablight.common.base.dataaddon.storeload.StoreLoadController;
import dev.tablight.test.dummies.DataAddonDummy;
import dev.tablight.test.dummies.DataAddonDummyLookup;

class StoreLoadControllerTest {
	TypeRegistry typeRegistry;
	TypeHolder holder;
	StoreLoadController controller;

	@BeforeEach
	void before() {
		typeRegistry = new DefaultTypeRegistry();
		holder = new ConcurrentTypeHolder();
		holder.addTypeRegistry(typeRegistry);
		typeRegistry.addRegistrableHolder(holder);
		controller = new DefaultStoreLoadController();
		controller.addRegistrableHolder(holder);
		typeRegistry.register(DataAddonDummy.class);
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
		var dummy = typeRegistry.newInstance(DataAddonDummy.class);
		var dummy1 = typeRegistry.newInstance(DataAddonDummy.class);
		controller.store(DataAddonDummy.class);
		assertEquals("store", dummy.getSomeString());
		assertEquals("store", dummy1.getSomeString());
	}

	@Test
	void checkLoad() {
		var dummy = typeRegistry.newInstance(DataAddonDummy.class);
		var dummy1 = typeRegistry.newInstance(DataAddonDummy.class);
		controller.load(DataAddonDummy.class);
		assertEquals("load", dummy.getSomeString());
		assertEquals("load", dummy1.getSomeString());
	}

	@Test
	void checkLookup() {
		controller.lookup(new DataAddonDummyLookup());
		controller.load(DataAddonDummy.class);
		DataAddonDummy dummy = holder.getHeld(DataAddonDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}

	@Test
	void checkLookupAndLoad() {
		controller.lookupAndLoad(new DataAddonDummyLookup());
		DataAddonDummy dummy = holder.getHeld(DataAddonDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}

	@Test
	void checkLookupClass() {
		controller.lookup(DataAddonDummy.class);
		controller.load(DataAddonDummy.class);
		DataAddonDummy dummy = holder.getHeld(DataAddonDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}

	@Test
	void checkLookupAndLoadClass() {
		controller.lookupAndLoad(DataAddonDummy.class);
		DataAddonDummy dummy = holder.getHeld(DataAddonDummy.class).stream().findFirst().get();
		assertEquals("native1", dummy.getSomeNativeStringData());
		assertEquals("load", dummy.getSomeString());
	}
}
