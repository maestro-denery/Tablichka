package dev.tablight.common.base.dataaddon.holder;

import java.util.Collection;

public final class HolderEvent {
	private Collection<Object> registrables;
	private Class<?> registrableType;

	public Collection<Object> getRegistrables() {
		return registrables;
	}

	public void setRegistrables(Collection<Object> registrables) {
		this.registrables = registrables;
	}

	public Class<?> getRegistrableType() {
		return registrableType;
	}

	public void setRegistrableType(Class<?> registrableType) {
		this.registrableType = registrableType;
	}
}
