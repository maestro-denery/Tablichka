import com.destroystokyo.paper.entity.Pathfinder;
import io.denery.entityregistry.behaviour.BehaviourBuilder;
import org.bukkit.entity.LivingEntity;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

public class CheckAPI {
    @Test
    void behaviour() {
        Flux<Consumer<LivingEntity>> flux = Flux.just(action1Entity -> {
            Pathfinder pathfinder = BehaviourBuilder.getPathFinder(action1Entity);
            //pathfinder.moveTo(new Location(, 0, 0,0));
        });

        BehaviourBuilder behaviour = BehaviourBuilder.newBuilder()
                .setActions(flux)
                .build();

        //CustomizableEntity customizableEntity =

    }
}
