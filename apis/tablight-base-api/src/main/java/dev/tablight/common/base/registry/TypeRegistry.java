package dev.tablight.common.base.registry;

import com.mojang.datafixers.util.Pair;

import java.util.function.Supplier;

public abstract class TypeRegistry {
	public abstract void register(Class<? extends Registrable> registrableType);

	public abstract boolean isRegistered(Class<? extends Registrable> registrableType);

	public abstract <T extends Registrable> T newRegisteredInstance(Class<T> registrableType);

	public abstract <T extends Registrable> T newRegisteredInstance(String identifier);
	
	public abstract String getIdentifier(Class<? extends Registrable> registrableType);
	
	public abstract Class<? extends Registrable> getRegistrableType(String identifier);

	public abstract <T extends Registrable> Pair<Supplier<T>, Supplier<T>> getLazyStoreLoad(Class<T> registrableType);
	
	public abstract void addRegistrableHolder(RegistrableHolder registrableHolder);
}