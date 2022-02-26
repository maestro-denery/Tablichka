package dev.tablight.common.base.registry.annotation;

import dev.tablight.common.base.registry.TypeRegistry;
import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.StoreLoadController;
import dev.tablight.common.base.registry.storeload.StoreLoadLookup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark your class with this annotation if it represents object with additional data over some other object data
 * to connect and use it with {@link TypeRegistry}, {@link TypeHolder}, {@link StoreLoadController} and other infrastructure.
 * @see dev.tablight.common.base.registry.DataAddonBootstrap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataAddon {
	/**
	 * @return Unique String identifier, used in {@link TypeRegistry} and {@link TypeHolder} 
	 */
	String identifier();

	/**
	 * @return Unique group tag, needs to mark relation between this DataAddon and {@link TypeRegistry}, {@link TypeHolder}, {@link StoreLoadController}.
	 */
	String groupTag();

	/**
	 * @return "Native" class which this DataAddon decorate with additional data.
	 */
	Class<?> nativeClass();

	/**
	 * @return "Lookup" which looks for "native" data, makes objects with this type and holds them in connected holders by group tag. 
	 */
	Class<? extends StoreLoadLookup<?, ?>> lookup();
}
