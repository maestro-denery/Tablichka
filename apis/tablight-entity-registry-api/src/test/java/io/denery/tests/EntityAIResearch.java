package io.denery.tests;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.App2;
import com.mojang.datafixers.kinds.K2;
import com.mojang.datafixers.optics.Optic;
import com.mojang.datafixers.optics.Optics;
import com.mojang.datafixers.optics.profunctors.Profunctor;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

class EntityAIResearch {
	
	@Test
	void brainResearch() {
		Optic<Profunctor.Mu, Integer, Integer, String, String> optic = new Optic<Profunctor.Mu, Integer, Integer, String, String>() {
			@Override
			public <P extends K2> Function<App2<P, String, String>, App2<P, Integer, Integer>> eval(App<? extends Profunctor.Mu, P> proof) {
				return null;
			}
		};
	}
}
