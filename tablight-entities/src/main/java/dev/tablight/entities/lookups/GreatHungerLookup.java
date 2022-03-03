package dev.tablight.entities.lookups;

import java.util.Collection;
import java.util.function.Supplier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;

import dev.tablight.entities.EntitiesPlugin;
import dev.tablight.entities.impls.GreatHunger;

public class GreatHungerLookup extends LivingEntityLookup<GreatHunger> {
	@Override
	public Supplier<Collection<GreatHunger>> lookup() {
		allEntitiesStream
				.filter(entity -> entity instanceof Silverfish)
				.filter(entity -> entity.getEntityData().get(EntitiesPlugin.getAccessors().get(entity.getClass())).contains("tablight-entity"));

		return () -> null;
	}

	@Override
	public Collection<LivingEntity> getNatives() {
		return null;
	}
}
