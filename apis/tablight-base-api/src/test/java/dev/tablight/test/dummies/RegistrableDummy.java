package dev.tablight.test.dummies;

import dev.tablight.common.base.registry.Registrable;

import java.util.Objects;

public class RegistrableDummy implements Registrable {
	private String someString;

	public String getSomeString() {
		return someString;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegistrableDummy dummy = (RegistrableDummy) o;
		return Objects.equals(someString, dummy.someString);
	}

	@Override
	public int hashCode() {
		return Objects.hash(someString);
	}
}
