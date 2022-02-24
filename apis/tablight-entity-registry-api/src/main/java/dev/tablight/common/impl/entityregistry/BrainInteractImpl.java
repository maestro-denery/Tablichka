package dev.tablight.common.impl.entityregistry;

import dev.tablight.common.api.entityregistry.BrainInteract;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;

public class BrainInteractImpl implements BrainInteract {
	private static BrainInteractImpl instance;

	public static BrainInteractImpl getInstance() {
		if (instance == null) instance = new BrainInteractImpl();
		return instance;
	}

	public <U extends LivingEntity> Brain<U> getBrain(org.bukkit.entity.LivingEntity bukkitLivingEntity) {
		return (Brain<U>) ((LivingEntity) bukkitLivingEntity).getBrain();
	}
}
