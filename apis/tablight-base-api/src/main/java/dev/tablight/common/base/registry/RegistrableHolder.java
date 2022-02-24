package dev.tablight.common.base.registry;

import java.util.Collection;

public abstract class RegistrableHolder {

	public abstract <T extends Registrable> void hold(T instance);

	public abstract <T extends Registrable> void release(T instance);
	
	public abstract <T extends Registrable> Collection<T> getHeld(Class<T> registrableType);

	public abstract <T extends Registrable> Collection<T> getHeld(String identifier);

	public abstract <T extends Registrable> boolean containsInstance(T registrable);

	public abstract void addTypeRegistry(TypeRegistry typeRegistry);

	public abstract void clearHeld();
}
