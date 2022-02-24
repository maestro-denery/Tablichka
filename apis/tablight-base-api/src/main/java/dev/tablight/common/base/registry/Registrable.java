package dev.tablight.common.base.registry;

import java.util.function.Supplier;

public interface Registrable {
	String identifier();

	Registrable store();

	Registrable load();

	default Supplier<Registrable> lazystore() {
		return this::store;
	}

	default Supplier<Registrable> lazyload() {
		return this::load;
	}
}
