package dev.tablight.test.dummies;

import java.util.Collection;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import dev.tablight.common.base.registry.storeload.StoreLoadLookup;

public class RegistrableDummyLookup implements StoreLoadLookup<RegistrableDummy, RegistrableDummyLookup.NativeDummy> {

	private final Collection<NativeDummy> nativeDummiesContainer = Lists.newArrayList(
			new NativeDummy("native1")
	); 
	
	@Override
	public Supplier<Collection<RegistrableDummy>> lookup() {
		return () -> nativeDummiesContainer.stream().map(nativeD -> {
			var dummy = new RegistrableDummy();
			dummy.setSomeNativeStringData(nativeD.getSomeNativeString());
			return dummy;
		}).toList();
	}

	@Override
	public Collection<NativeDummy> getNatives() {
		return nativeDummiesContainer;
	}
	
	public static class NativeDummy {
		private final String someNativeString;
		public NativeDummy(String nativeString) {
			this.someNativeString = nativeString;
		} 

		public String getSomeNativeString() {
			return someNativeString;
		}
	}
}
