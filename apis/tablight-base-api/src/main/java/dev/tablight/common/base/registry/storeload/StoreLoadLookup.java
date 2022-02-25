package dev.tablight.common.base.registry.storeload;

import java.util.Collection;
import java.util.function.Supplier;

public interface StoreLoadLookup<T, N> {
	Supplier<Collection<T>> lookup();
	
	Collection<N> getNatives();
}
