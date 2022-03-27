package dev.tablight.entities.lookups.monster;

import java.util.stream.Stream;

import dev.tablight.dataaddon.mark.Mark;

import net.minecraft.world.entity.animal.Squid;

import dev.tablight.entities.impls.monster.LochnessMonster;
import dev.tablight.entities.lookups.LivingEntityLookup;
import dev.tablight.entities.registries.EntityTypeRegistry;

public class LochnessMonsterLookup extends LivingEntityLookup<LochnessMonster, Squid> {
	@Override
	public Mark<LochnessMonster, Squid> mark() {
		return Mark.create(
				squid -> {
					squid.setHealth(80);
					LochnessMonster lochnessMonster = new LochnessMonster();
					lochnessMonster.setOrigin(squid);
					return lochnessMonster;
				},
				lochnessMonster -> isCustom(lochnessMonster.getOrigin(), lochnessMonster.getClass())
		);
	}

	@Override
	public Stream<Squid> getNatives() {
		return allEntitiesStream;
	}
}
