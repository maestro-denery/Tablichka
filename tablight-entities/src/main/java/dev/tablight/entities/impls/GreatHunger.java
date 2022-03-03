package dev.tablight.entities.impls;

import net.minecraft.world.entity.monster.Silverfish;

import dev.tablight.common.base.dataaddon.annotation.DataAddon;
import dev.tablight.common.base.dataaddon.annotation.Load;
import dev.tablight.common.base.dataaddon.annotation.Store;
import dev.tablight.entities.lookups.GreatHungerLookup;

@DataAddon(
		identifier = "great-hunger",
		groupTag = "entities-group",
		nativeClass = Silverfish.class,
		lookup = GreatHungerLookup.class
)
public class GreatHunger {
	
	
	private Silverfish silverfish;

	public void setNative(Silverfish silverfish) {
		this.silverfish = silverfish;
	}

	@Load
	void load() {

	}

	@Store
	void store() {

	}
}
