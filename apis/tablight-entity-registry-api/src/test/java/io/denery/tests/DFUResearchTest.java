package io.denery.tests;

import com.mojang.datafixers.optics.Adapter;
import com.mojang.datafixers.optics.Lens;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.Prism;
import com.mojang.datafixers.optics.Traversal;
import com.mojang.datafixers.util.Either;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class DFUResearchTest {
	
	@Test
	void someResearch() {
		Lens<DummyStructS, DummyStructT, String, Integer> lens = Optics.lens(DummyStructS::getS, (i, dummyS) -> {
			DummyStructT dummyStructT = new DummyStructT();
			dummyStructT.setI(Integer.valueOf(dummyS.getS()));
			dummyStructT.setStrings(dummyS.getStrings());
			return dummyStructT;
		});
		Prism<Optional<String>, Optional<Integer>, String, Integer> prism = Optics.prism(
				s -> s.<Either<Optional<Integer>, String>>map(Either::right)
						.orElseGet(() -> Either.left(s.map(Integer::valueOf)) /* It will anyway be null, mot sure about this. */),
				Optional::of
		);
		Adapter<DummyStructS, DummyStructT, String, Integer> adapter = Optics.adapter(
				DummyStructS::getS,
				integer -> {
					DummyStructT dummyStructT = new DummyStructT();
					dummyStructT.setI(integer);
					return dummyStructT;
				}
		);

		Traversal<DummyStructS, DummyStructT, String, Integer> traversal = Optics.toTraversal(lens);
	}

	public static final class DummyStructS {
		private String s;
		List<String> strings;

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}

		public List<String> getStrings() {
			return strings;
		}

		public void setStrings(List<String> strings) {
			this.strings = strings;
		}
	}

	public static final class DummyStructT {
		private int i;
		List<String> strings;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public List<String> getStrings() {
			return strings;
		}

		public void setStrings(List<String> strings) {
			this.strings = strings;
		}
	}

	@Test
	void regexCheck() {
		System.out.println("1.18.1".replace('.', '_').replaceAll("_.$", "_R"));
	}
}
