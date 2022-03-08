package dev.tablight.entities.impls.monster;

import dev.tablight.common.base.dataaddon.annotation.DataAddon;

import dev.tablight.entities.lookups.monster.LochnessMonsterLookup;

import net.minecraft.world.entity.animal.Squid;

@DataAddon(
		identifier = "LochnessMonster",
		groupTag = "entities-group",
		nativeClass = Squid.class,
		lookup = LochnessMonsterLookup.class
)
public class LochnessMonster {
	private Squid origin;

	public Squid getOrigin() {
		return origin;
	}

	public void setOrigin(Squid origin) {
		this.origin = origin;
	}
}
