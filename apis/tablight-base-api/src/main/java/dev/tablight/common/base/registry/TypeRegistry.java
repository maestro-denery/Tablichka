package dev.tablight.common.base.registry;

import com.mojang.datafixers.util.Pair;

import dev.tablight.common.base.registry.holder.RegistrableHolder;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Registry containing Unique Registrable types where you can register, instantiate "custom" types.
 * <p>
 * Examples:
 * <pre>{@code
 * 		TypeRegistry registry = GlobalTypeRegistry.getInstance();
 * 		registry.register(SomeRegistrableImpl.class); // It is important that you need to registry your class before occurring actions with this registrable.
 * 		SomeRegistrableImpl reg = registry.newInstance(SomeRegistrableImpl.class);
 * 		reg.someMethod();
 * }</pre>
 * <p>
 * Implement this class if you are making your API for registering "custom" types on top of Minecrafts' ones.
 * <h2>!!!IMPORTANT!!!</h2> Add holders for getting and handling your registered types using {@link #addRegistrableHolder(RegistrableHolder)} method.
 */
public abstract class TypeRegistry {
	/**
	 * Registers your types for further handling, storing and loading.
	 * @param registrableType non-abstract "custom" type implementing {@link Registrable} interface.
	 */
	public abstract void register(Class<? extends Registrable> registrableType);

	/**
	 * Checks if your {@link Registrable} implementation already registered.
	 * @param registrableType non-abstract "custom" type implementing {@link Registrable} interface.
	 * @return
	 */
	public abstract boolean isRegistered(Class<? extends Registrable> registrableType);

	/**
	 * Instantiates registered class and adding it into all added holders.
	 * @param registrableType registered non-abstract "custom" type implementing {@link Registrable} interface.
	 * @param <T> your exact type.
	 * @return Instance added into all holders added to this {@link TypeRegistry}.
	 */
	public abstract <T extends Registrable> T newInstance(Class<T> registrableType);

	/**
	 * Same as {@link #newInstance(Class)} but with unique identifier specified in {@link Registrable#identifier()}
	 * @param identifier Unique identifier.
	 * @param <T> your exact type.
	 * @return Instance added into all holders added to this {@link TypeRegistry}.
	 */
	public abstract <T extends Registrable> T newInstance(String identifier);

	/**
	 * Obtains unique identifier by its class. 
	 * @param registrableType registered non-abstract "custom" type implementing {@link Registrable} interface.
	 * @return unique identifier.
	 */
	public abstract String getIdentifier(Class<? extends Registrable> registrableType);

	/**
	 * Obtains {@link Registrable} class by its unique id.
	 * @param identifier unique id.
	 * @return {@link Registrable} class.
	 */
	public abstract Class<? extends Registrable> getRegistrableType(String identifier);

	/**
	 * Obtains lazy versions of store and load operations in specified {@link Registrable} type.
	 * @param registrableType registered non-abstract "custom" type implementing {@link Registrable} interface.
	 * @param <T> your exact type.
	 * @return Pair of lazy store/load operations, first - store, second - load.
	 */
	public abstract <T extends Registrable> Pair<Supplier<T>, Supplier<T>> getLazyStoreLoad(Class<T> registrableType);

	/**
	 * Adds {@link RegistrableHolder} to this {@link TypeRegistry} for handling.
	 * @param registrableHolder {@link RegistrableHolder} you prefer to add to this {@link TypeRegistry}
	 */
	public abstract void addRegistrableHolder(RegistrableHolder registrableHolder);

	/**
	 * Obtains RegistrableHolders added to this {@link TypeRegistry}.
	 * @return All added RegistrableHolders.
	 */
	public abstract Collection<RegistrableHolder> getRegistrableHolders();
	
	/**
	 * clear everything in this {@link TypeRegistry}
	 */
	public abstract void clearRegistry(); 
}
