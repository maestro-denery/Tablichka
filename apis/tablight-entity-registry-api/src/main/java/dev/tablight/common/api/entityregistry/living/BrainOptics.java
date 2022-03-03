package dev.tablight.common.api.entityregistry.living;

import com.mojang.datafixers.optics.Lens;

import dev.tablight.common.impl.entityregistry.living.BrainOpticsImpl;

import net.minecraft.world.entity.ai.Brain;

public interface BrainOptics {
	static BrainOptics getInstance() {
		return BrainOpticsImpl.INSTANCE;
	}
	
	Lens<org.bukkit.entity.LivingEntity, org.bukkit.entity.LivingEntity, Brain<?>, Brain<?>> getBrainLens();
}
