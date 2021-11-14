package io.denery.entityregistry.behaviour;

import com.destroystokyo.paper.entity.Pathfinder;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.tablichka.architecture.components.BiTuple;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Class representing Entity's behaviour.
 * "Behaviour" is a sequence of "Actions", and Actions is
 * Consumer of PathFinding/LivingEntity actions.
 */
public final class BehaviourBuilder extends AbstractBehaviour {
    private BehaviourBuilder() {}



    public static BehaviourBuilder newBuilder() {
        return new BehaviourBuilder();
    }

    /**
     * For those badass guys who have fully reactive plugin event system.
     * @param actionSequence reactive sequence of actions to complete.
     * @return Builder instance with changed value.
     */
    public BehaviourBuilder setActions(Flux<Consumer<LivingEntity>> actionSequence) {
        actions = actionSequence;
        return this;
    }

    /**
     * Build actions through list to those who doesn't like reactive programming.
     * @param listActions list of actions to complete.
     * @return Builder instance with changed value.
     */
    public BehaviourBuilder setActionsList(List<Consumer<LivingEntity>> listActions) {
        actions = Flux.fromIterable(listActions);
        return this;
    }

    public io.denery.entityregistry.behaviour.BehaviourBuilder build() {
        return io.denery.entityregistry.behaviour.BehaviourBuilder.this;
    }
}

