package dev.tablight.common.api.entityregistry;

import dev.tablight.common.impl.entityregistry.BrainInteractImpl;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;

public interface BrainInteract {
	static BrainInteract getInstance() {
		return BrainInteractImpl.getInstance();
	}
	
	<U extends LivingEntity> Brain<U> getBrain(org.bukkit.entity.LivingEntity bukkitLivingEntity);
	
	
}
