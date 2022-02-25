package dev.tablight.test.registries;

import dev.tablight.common.base.registry.annotation.group.Holder;
import dev.tablight.common.base.registry.holder.ConcurrentRegistrableHolder;

@Holder("dummyGroup")
public class DummyHolder extends ConcurrentRegistrableHolder {
	private static DummyHolder instance;
	public static DummyHolder getInstance() {
		if (instance == null) instance = new DummyHolder();
		return instance;
	}
}
