package dev.tablight.test.dummies;

import dev.tablight.common.base.registry.Registrable;
import dev.tablight.common.base.registry.storeload.StoreLoadLookup;

public class RegistrableDummy implements Registrable {
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

	@Override
	public String identifier() {
		return "dummy";
	}

	@Override
	public RegistrableDummy store() {
		someString = "store";
		return this;
	}

	@Override
	public RegistrableDummy load() {
		someString = "load";
		return this;
	}

	@Override
	public StoreLoadLookup<RegistrableDummy, RegistrableDummyLookup.NativeDummy> getlookup() {
		return new RegistrableDummyLookup();
	}
}
