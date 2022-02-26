package dev.tablight.common.base.global;

import dev.tablight.common.base.dataaddon.annotation.group.GroupContainer;

/**
 * Global Group container for TabLight infrastructure.
 */
public final class GlobalGroupContainer extends GroupContainer {
	private static GlobalGroupContainer instance;
	public static GlobalGroupContainer getInstance() {
		if (instance == null) instance = new GlobalGroupContainer();
		return instance;
	}
	
	private GlobalGroupContainer() {}
}
