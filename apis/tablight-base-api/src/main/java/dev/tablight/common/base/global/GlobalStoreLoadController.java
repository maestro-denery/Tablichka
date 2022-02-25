package dev.tablight.common.base.global;

import dev.tablight.common.base.registry.holder.TypeHolder;
import dev.tablight.common.base.registry.storeload.DefaultStoreLoadController;
import dev.tablight.common.base.registry.RegistryException;

public final class GlobalStoreLoadController extends DefaultStoreLoadController {
	private static GlobalStoreLoadController instance;
	public static GlobalStoreLoadController getInstance() {
		if (instance == null) {
			instance = new GlobalStoreLoadController();
			instance.holders.add(GlobalRegistrableHolder.getInstance());
		}
		return instance;
	}
	
	private GlobalStoreLoadController() {}

	@Override
	public void addRegistrableHolder(TypeHolder registrableHolder) {
		throw new RegistryException("Global store load controller has only one registrable holder!");
	}
}
