package dev.tablight.common.base.registry.storeload;

import dev.tablight.common.base.registry.Registrable;

import java.util.Collection;
import java.util.function.Supplier;

public interface StoreLoadLookup<N> {
	<T extends Registrable> Supplier<Collection<T>> lookup();
	
	Collection<N> getNatives();
}
