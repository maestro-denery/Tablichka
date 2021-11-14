package org.tablichka.entities.mobs.beahviours;

import com.destroystokyo.paper.entity.Pathfinder;
import io.denery.entityregistry.behaviour.Behaviour;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

public class GreatHungerBehaviour extends Behaviour {
    public GreatHungerBehaviour(Player player) {
        super(Flux.just(entity -> {
            Pathfinder pathfinder = getPathFinder(entity);
            pathfinder.moveTo(player);
        }));
    }
}
