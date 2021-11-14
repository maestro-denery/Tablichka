package io.denery.entityregistry.behaviour;

import org.bukkit.entity.LivingEntity;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public class Behaviour extends AbstractBehaviour {
    public Behaviour(Flux<Consumer<LivingEntity>> actions) {
        super.actions = actions;
    }

    public Behaviour(List<Consumer<LivingEntity>> listActions) {
        super.actions = Flux.fromIterable(listActions);
    }
}
