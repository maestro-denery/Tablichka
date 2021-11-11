package io.denery.entity;

import io.denery.behaviour.Behaviour;
import org.bukkit.entity.LivingEntity;

public record CustomizableEntityType(String name, LivingEntity origin, Behaviour behaviour) {

}
