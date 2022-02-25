package dev.tablight.common.base.registry.storeload;

import java.util.ArrayList;
import java.util.Collection;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.RegistryException;
import dev.tablight.common.base.registry.holder.RegistrableHolder;

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
	public <T extends Registrable> void lookupAndLoad(Class<T> registrableType) {
		try {
			lookupAndLoad(registrableType.getDeclaredConstructor().newInstance().getlookup());
		} catch (ReflectiveOperationException e) {
			throw new RegistryException("Can't obtain lookup of this registrable: " + registrableType);
		}
	}

	@Override
	public <T extends Registrable> void lookup(Class<T> registrableType) {
		try {
			lookup(registrableType.getDeclaredConstructor().newInstance().getlookup());
		} catch (ReflectiveOperationException e) {
			throw new RegistryException("Can't obtain lookup of this registrable: " + registrableType);
		}
	}

	@Override
	public <T extends Registrable, N> void lookupAndLoad(StoreLoadLookup<T, N> lookup) {
		lookup.lookup().get().forEach(registrable -> {
			holders.forEach(holder -> holder.hold(registrable));
			registrable.load();
		});
	}

	@Override
	public <T extends Registrable, N> void lookup(StoreLoadLookup<T, N> lookup) {
		lookup.lookup().get().forEach(registrable -> holders.forEach(holder -> holder.hold(registrable)));
	}

	@Override
	public void addRegistrableHolder(RegistrableHolder registrableHolder) {
		holders.add(registrableHolder);
	}

	@Override
	public Collection<RegistrableHolder> getRegistrableHolders() {
		return holders;
	}
}
