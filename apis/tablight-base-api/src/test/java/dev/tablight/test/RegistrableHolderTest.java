package dev.tablight.test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.tablight.common.base.registry.DefaultTypeRegistry;
import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.holder.ConcurrentRegistrableHolder;
import dev.tablight.common.base.registry.holder.RegistrableHolder;
import dev.tablight.test.dummies.RegistrableDummy;

public class RegistrableHolderTest {
	RegistrableHolder holder;
	TypeRegistry typeRegistry;

	@BeforeEach
	void before() {
		typeRegistry = new DefaultTypeRegistry();
		holder = new ConcurrentRegistrableHolder();
		holder.addTypeRegistry(typeRegistry);
		typeRegistry.addRegistrableHolder(holder);
		typeRegistry.register(RegistrableDummy.class);
	}

	@AfterEach
	void after() {
		holder.clearHeld();
		((ConcurrentRegistrableHolder) holder).shutdownDisruptor();
		typeRegistry.clearRegistry();
		typeRegistry = null;
		holder = null;
	}

	@Test
	void checkHold() {
		assertDoesNotThrow(() -> holder.hold(new RegistrableDummy()));
	}

	@Test
	void checkHoldEquality() {
		var dummy = new RegistrableDummy();
		holder.hold(dummy);
		assertEquals(dummy, holder.getHeld(RegistrableDummy.class).toArray()[0]);
	}

	@Test
	void checkGetType() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		assertIterableEquals(List.of(dummy), holder.getHeld(RegistrableDummy.class));
	}

	@Test
	void checkGetID() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		assertIterableEquals(List.of(dummy), holder.getHeld("dummy"));
	}

	@Test
	void checkContains() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		assertTrue(holder.containsInstance(dummy));
	}

	@Test
	void checkRelease() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newInstance(RegistrableDummy.class);
		holder.release(dummy);
		assertFalse(holder.containsInstance(dummy));
		assertTrue(holder.containsInstance(dummy1));
	}

	@Test
	void checkReleaseClass() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newInstance(RegistrableDummy.class);
		holder.release(RegistrableDummy.class);
		assertFalse(holder.containsInstance(dummy));
		assertFalse(holder.containsInstance(dummy1));
	}

	@Test
	void checkReleaseID() {
		var dummy = typeRegistry.newInstance(RegistrableDummy.class);
		var dummy1 = typeRegistry.newInstance(RegistrableDummy.class);
		holder.release("dummy");
		assertFalse(holder.containsInstance(dummy));
		assertFalse(holder.containsInstance(dummy1));
	}

	@Test
	void checkEventHandlerType() {
		holder.handle(((event, sequence, endOfBatch) -> {
			assertEquals(RegistrableDummy.class, event.getRegistrableType());
		}));
		holder.hold(new RegistrableDummy());
	}

	@Test
	void checkEventHandlerCollection() {
		var dummy = new RegistrableDummy();
		holder.handle(((event, sequence, endOfBatch) -> {
			assertIterableEquals(List.of(dummy), event.getRegistrables());
		}));
		holder.hold(dummy);
	}
}
