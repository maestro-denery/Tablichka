package dev.tablight.common.base.registry;

import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.StoreLoadController;
import dev.tablight.common.base.registry.storeload.StoreLoadLookup;

import java.util.function.Supplier;

/**
 * Interface representing object which can be registered. Stored and loaded from a disk.
 * It is the main component of registry library, if you have some objects based on usual minecraft components 
 * which you need to save after server shutting down and load after starting up, implement this interface.
 * <p>
 * @see TypeRegistry Registering "custom" types
 * @see TypeHolder Holding and handling instances of registered "custom" types
 * @see StoreLoadController Importing "native" data to registered "custom" types
 */
public interface Registrable {
	/**
	 * Method returning unique String identifier used in {@link TypeRegistry} and {@link TypeHolder}
	 * @return unique identifier.
	 */
	String identifier();

	/**
	 * Method which must store "custom" data to a disk.
	 * @return Registrable with stored data.
	 * @see StoreLoadController for storing and loading of holder's "custom" type instances.
	 */
	Registrable store();

	/**
	 * Method which must load "custom" data from a disk to this Registrable.
	 * @return Registrable with loaded data.
	 * @see StoreLoadController for storing and loading of holder's "custom" type instances.
	 */
	Registrable load();

	/**
	 * <h2>!Warning! Most of the cases this method called in newly instantiated class,
	 * ({@link StoreLoadController#lookup(Class)}) so DO NOT use references to fields in it!</h2>
	 * Get lookup in implemented {@link Registrable} type.
	 * @return lookup which makes instances of implemented class.
	 * @see StoreLoadController store load mechanism.
	 */
	StoreLoadLookup<? extends Registrable, ?> getLookup();

	default Supplier<Registrable> lazyStore() {
		return this::store;
	}

	default Supplier<Registrable> lazyLoad() {
		return this::load;
	}
}
