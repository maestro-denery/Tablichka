package dev.tablight.common.base.registry.storeload;

import java.util.ArrayList;
import java.util.Collection;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.RegistrableHolder;

public class DefaultStoreLoadController extends StoreLoadController {
	protected final Collection<RegistrableHolder> holders = new ArrayList<>();

	@Override
	public void store(Class<? extends Registrable> registrableType) {
		holders.forEach(holder -> holder.getHeld(registrableType).forEach(Registrable::store));
	}

	@Override
	public void load(Class<? extends Registrable> registrableType) {
		holders.forEach(holder -> holder.getHeld(registrableType).forEach(Registrable::load));
	}

	@Override
	public <T extends Registrable, N> void lookupAndLoad(Class<T> registrableType, StoreLoadLookup<T, N> lookup) {
		this.lookup(lookup);
		this.load(registrableType);
	}

	@Override
	public <T extends Registrable, N> void lookup(StoreLoadLookup<T, N> lookup) {
		lookup.lookup().get().forEach(registrable -> holders.forEach(holder -> holder.hold(registrable)));
	}

	@Override
	public void addRegistrableHolder(RegistrableHolder registrableHolder) {
		holders.add(registrableHolder);
	}
}
