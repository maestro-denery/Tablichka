package dev.tablight.common.base.registry.storeload;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.RegistrableHolder;

public abstract class StoreLoadController {
	public abstract void store(Class<? extends Registrable> registrableType);

	public abstract void load(Class<? extends Registrable> registrableType);
	
	public abstract <N> void lookup(StoreLoadLookup<N> lookup);
	
	public abstract void addRegistrableHolder(RegistrableHolder registrableHolder);
}
