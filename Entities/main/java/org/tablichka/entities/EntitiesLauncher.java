package org.tablichka.entities;

import io.denery.entityregistry.EntityTypeRegistry;
import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntity;
import io.denery.entityregistry.entity.CustomizableEntityTypeBuilder;
import io.denery.entityregistry.spawn.CustomizableSpawn;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tablichka.architecture.Launcher;
import org.tablichka.entities.commands.EntitiesCommands;
import org.tablichka.entities.mobs.beahviours.GreatHungerBehaviour;

import java.util.Objects;
import java.util.Optional;

public final class EntitiesLauncher extends JavaPlugin implements Launcher {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka-Entities");

    AbstractCustomizableEntityType greatHunger = CustomizableEntityTypeBuilder.newBuilder()
            .setID("mobc")
            .setOriginType(EntityType.RABBIT)
            .build();

    private CustomizableSpawn.CustomizableSpawnBuilder spawnbuilder;

    @Override
    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void loadCommands() {
        Objects.requireNonNull(getCommand("erspawn")).setExecutor(EntitiesCommands.getInstance());
    }

    @Override
    public void onEnable() {
        logger.info("Loading Entities Module.");
        logger.info("Version dev-1.0.016");

        EntityTypeRegistry registry = EntityTypeRegistry.newRegistry()
                .register(greatHunger)
                .apply();

        spawnbuilder = CustomizableSpawn.newBuilder()
                .setMaxPerChunk(40)
                .setEntities(registry.getRegisteredEntitiesList());

        loadEvents();
        loadCommands();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public class Events implements Listener {

        @EventHandler
        public void onMoveTest(PlayerMoveEvent e) {
            /*
            Optional<CustomizableEntity<?>> optionalCEntity = spawnbuilder.build()
                    .apply(getServer(), e.getPlayer().getLocation());

            if (optionalCEntity.isPresent()) {
                CustomizableEntity<?> entity = optionalCEntity.get();
                entity.setBehaviour(new GreatHungerBehaviour(e.getPlayer()));
                entity.modelEntity();
                entity.applyBehaviour(1, 1, entity.getOriginEntity().get());
            }
             */
        }
    }

}
