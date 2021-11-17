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

public abstract class AbstractBehaviour implements Consumer<BiTuple<Integer, LivingEntity>> {
    protected Flux<Consumer<LivingEntity>> actions;

    /**
     * Makes completions of random origin's action with randomness value.
     *
     * @param completions How many completions of actions will occur.
     * @param randomness How rare actions will choose for complete. (greater == rare)
     * @param origin Entity which will do these actions.
     */
    public void acceptRandom(int completions, int randomness, LivingEntity origin) {
        Flux<Consumer<LivingEntity>> taken = actions.filter(livingEntity -> {
            Random random = new Random();
            OptionalInt i = random.ints(0, randomness)
                    .findFirst();
            if (i.isEmpty()) return false;
            return i.getAsInt() == 0;
        });
        taken.take(completions).subscribe(s -> s.accept(origin));
    }

    /**
     * Completes a number of actions with given LivingEntity.
     * @param origin Tuple of Two values: Integer - how many actions will be completed at this method,
     *               and origin - entity which will do these actions.
     */
    @Override
    public void accept(BiTuple<Integer, LivingEntity> origin) {
        actions.take(origin.getFirst()).subscribe(s -> s.accept(origin.getSecond()));
    }

    public static Pathfinder getPathFinder(LivingEntity entity) {
        try {
            return ((Mob) entity).getPathfinder();
        } catch (ClassCastException e) {
            throw new RuntimeException("Cannot cast LivingEntity to Mob! Is it Mob?");
        }
    }

    public Flux<Consumer<LivingEntity>> getActions() {
        return actions;
    }

    public List<Consumer<LivingEntity>> getActionList() {
        return actions.toStream().toList();
    }
}
