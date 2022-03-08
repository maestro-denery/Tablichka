package io.denery.tests;

import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.optics.Optics;
import org.junit.jupiter.api.Test;

class DFUResearchTest {
	
	@Test
	void someResearch() {
		Lens<DummyStructS, DummyStructT, String, Integer> lens = Optics.lens(DummyStructS::getS, (i, dummyS) -> {
			DummyStructT dummyStructT = new DummyStructT();
			dummyStructT.setI(Integer.valueOf(dummyS.getS()) + i);
			return dummyStructT;
		});
		var dummySInstance = new DummyStructS();
		dummySInstance.setS("321");
		System.out.println("converted: " + lens.update(123, dummySInstance).getI()); // converted: 444
		System.out.println("view: " + lens.view(dummySInstance)); // view: 321
	}

	public static final class DummyStructS {
		private String s;

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}
	}

	public static final class DummyStructT {
		private int i;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}

	@Test
	void regexCheck() {
		System.out.println("1.18.1".replace('.', '_').replaceAll("_.$", "_R"));
	}
}
