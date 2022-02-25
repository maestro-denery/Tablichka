package dev.tablight.common.base.registry.holder;

import dev.tablight.common.base.registry.Registrable;

import java.util.Collection;

public final class HolderEvent {
	private Collection<? extends Registrable> registrables;
	private Class<? extends Registrable> registrableType;

	public Collection<? extends Registrable> getRegistrables() {
		return registrables;
	}

	public void setRegistrables(Collection<? extends Registrable> registrables) {
		this.registrables = registrables;
	}

	public Class<? extends Registrable> getRegistrableType() {
		return registrableType;
	}

	public void setRegistrableType(Class<? extends Registrable> registrableType) {
		this.registrableType = registrableType;
	}
}
