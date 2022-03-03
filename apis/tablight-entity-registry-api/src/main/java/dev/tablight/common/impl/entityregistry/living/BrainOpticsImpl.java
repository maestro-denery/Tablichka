package dev.tablight.common.impl.entityregistry.living;

import com.mojang.datafixers.optics.Lens;

import com.mojang.datafixers.optics.Optics;

import dev.tablight.common.api.entityregistry.living.BrainOptics;

import net.minecraft.world.entity.ai.Brain;

import org.bukkit.entity.LivingEntity;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class BrainOpticsImpl implements BrainOptics {
	public static final BrainOpticsImpl INSTANCE = new BrainOpticsImpl();
	private static final VarHandle LIVING_ENTITY_BRAIN_VH;

	static {
		try {
			LIVING_ENTITY_BRAIN_VH = MethodHandles.privateLookupIn(net.minecraft.world.entity.LivingEntity.class, MethodHandles.lookup())
					.findVarHandle(net.minecraft.world.entity.LivingEntity.class, "brain", Brain.class);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	@Override
	public Lens<LivingEntity, LivingEntity, Brain<?>, Brain<?>> getBrainLens() {
		return Optics.lens(
				livingEntity -> ((net.minecraft.world.entity.LivingEntity) livingEntity).getBrain(),
				(brain, livingEntity) -> {
					LIVING_ENTITY_BRAIN_VH.set(((net.minecraft.world.entity.LivingEntity) livingEntity), brain);
					return livingEntity;
				}
		);
	}
}
