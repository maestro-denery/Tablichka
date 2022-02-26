package dev.tablight.common.base.registry.holder;

import java.util.Collection;

public final class HolderEvent {
	private Collection<?> registrables;
	private Class<?> registrableType;

	public Collection<?> getRegistrables() {
		return registrables;
	}

	public void setRegistrables(Collection<?> registrables) {
		this.registrables = registrables;
	}

	public Class<?> getRegistrableType() {
		return registrableType;
	}

	public void setRegistrableType(Class<?> registrableType) {
		this.registrableType = registrableType;
	}
}
