package dev.tablight.entities.lookups.monster;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.world.entity.animal.Squid;

import dev.tablight.entities.impls.monster.LochnessMonster;
import dev.tablight.entities.lookups.LivingEntityLookup;
import dev.tablight.entities.registries.EntityTypeRegistry;

public class LochnessMonsterLookup extends LivingEntityLookup<LochnessMonster, Squid> {
	private final Stream<Squid> natives = allEntitiesStream
			.filter(entity -> entity instanceof Squid)
			.map(entity -> ((Squid) entity))
			.filter(squid -> isCustom(squid, LochnessMonster.class));
	
	@Override
	public Supplier<Collection<LochnessMonster>> lookup() {
		return () -> natives.map(squid -> {
			squid.setHealth(80);

			LochnessMonster lochnessMonster = bootstrap.getRegistry(EntityTypeRegistry.class).newInstance(LochnessMonster.class);
			lochnessMonster.setOrigin(squid);
			return lochnessMonster;
		}).toList();
	}

	@Override
	public Stream<Squid> getNatives() {
		return natives;
	}
}
