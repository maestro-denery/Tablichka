package dev.tablight.test.dummies;

import dev.tablight.common.base.registry.annotation.DataAddon;
import dev.tablight.common.base.registry.annotation.Load;
import dev.tablight.common.base.registry.annotation.Store;

@DataAddon(
		identifier = "dummy",
		groupTag = "dummyGroup",
		nativeClass = RegistrableDummyLookup.NativeDummy.class,
		lookup = RegistrableDummyLookup.class
)
public class RegistrableDummy {
	private String someString;
	private String someNativeStringData;

	public String getSomeString() {
		return someString;
	}

	public String getSomeNativeStringData() {
		return someNativeStringData;
	}

	public void setSomeNativeStringData(String someNativeStringData) {
		this.someNativeStringData = someNativeStringData;
	}

	@Store
	public void store() {
		someString = "store";
	}

	@Load
	public void load() {
		someString = "load";
	}
}
