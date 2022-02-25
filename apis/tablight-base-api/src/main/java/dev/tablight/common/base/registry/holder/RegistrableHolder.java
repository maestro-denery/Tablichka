package dev.tablight.common.base.registry.holder;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.TypeRegistry;

import java.util.Collection;

/**
 * Class "holding" instances of {@link TypeRegistry},
 * it is needed when you need to handle your "custom" types, for example
 */
public abstract class RegistrableHolder {

	/**
	 * "Holds" your {@link Registrable} instance until {@link #release(Registrable)}  or {@link #clearHeld()}.
	 * After that you can get collection of this type instances using {@link #getHeld(Class)}.
	 * @param instance Instance you want to hold.
	 * @param <T> Type of Instance you want to hold.
	 */
	public abstract <T extends Registrable> void hold(T instance);

	/**
	 * Removes your {@link Registrable} from holding in this holder.
	 * @param instance Instance you want to release.
	 * @param <T> Type of instance you want to release.
	 */
	public abstract <T extends Registrable> void release(T instance);

	/**
	 * Removes your {@link Registrable} type from holding in this holder. 
	 * @param registrableType type of Instance you want to release.
	 * @param <T> Type of instance toy want to release.
	 */
	public abstract <T extends Registrable> void release(Class<T> registrableType);

	/**
	 * Same as {@link #release(Class)} but with String identifier.
	 * @param identifier Identifier of instances you want to release.
	 */
	public abstract void release(String identifier);

	/**
	 * Checks if this holder contains the following instance.
	 * @param registrable {@link Registrable} instance you want to check.
	 * @param <T> exact type.
	 * @return true if holder contains this instance.
	 */
	public abstract <T extends Registrable> boolean containsInstance(T registrable);

	/**
	 * Obtains all elements of type you want held by this holder.
	 * @param registrableType {@link Registrable} Class of Instances you want to get.
	 * @param <T> Exact type of registrable.
	 * @return All instances held by this holder of your specific type.
	 */
	public abstract <T extends Registrable> Collection<T> getHeld(Class<T> registrableType);

	/**
	 * Same as {@link #getHeld(Class)} but get held instances by unique identifier.
	 * @param identifier unique identifier specified in {@link Registrable#identifier()}
	 * @param <T> exact type of {@link Registrable}
	 * @return All instances held by this holder of your specific type.
	 */
	public abstract <T extends Registrable> Collection<T> getHeld(String identifier);

	/**
	 * Adds TypeRegistry checks to this holder, if you are making your implementations you need to specify TypeRegistries for your holder. 
	 * @param typeRegistry {@link TypeRegistry} you want to check in this holder.
	 */
	public abstract void addTypeRegistry(TypeRegistry typeRegistry);

	/**
	 * Obtains TypeRegistries added in this {@link RegistrableHolder}
	 * @return All added TypeRegistries.
	 */
	public abstract Collection<TypeRegistry> getTypeRegistries();

	/**
	 * Clears all held instances from this holder.
	 */
	public abstract void clearHeld();
}
