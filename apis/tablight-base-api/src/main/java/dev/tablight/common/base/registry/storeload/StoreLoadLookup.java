package dev.tablight.common.base.registry.storeload;

import dev.tablight.common.base.registry.Registrable;

import java.util.Collection;
import java.util.function.Supplier;

public interface StoreLoadLookup<T extends Registrable, N> {
	Supplier<Collection<T>> lookup();
	
	Collection<N> getNatives();
}
