package dev.tablight.common.base.dataaddon.storeload;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 
 * @param <T> DataAddon you want to lookup.
 * @param <N> Native type of DataAddon.
 */
public interface StoreLoadLookup<T, N> {
	/**
	 * @return lazy lookup obtaining instances of DataAddon instances
	 */
	Supplier<Collection<T>> lookup();

	/**
	 * @return Natives matching DataAddon requirements.
	 */
	Collection<N> getNatives();
}
