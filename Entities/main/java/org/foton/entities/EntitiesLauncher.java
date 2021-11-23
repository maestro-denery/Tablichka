package org.foton.entities;

import com.ticxo.modelengine.api.event.ModelEngineInitializeEvent;
import io.denery.entityregistry.EntityTypeRegistry;
import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntityTypeBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.foton.entities.commands.EntitiesCommands;
import org.foton.entities.scala.ScalaEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.foton.architecture.Launcher;

import java.util.Objects;

public final class EntitiesLauncher extends JavaPlugin implements Launcher {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka-Entities");

    AbstractCustomizableEntityType greatHunger = CustomizableEntityTypeBuilder.newBuilder()
            .setID("mobc")
            .setOriginType(EntityType.OCELOT)
            .build();

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
        logger.info("Version dev-1.0.019");

        loadEvents();
        loadCommands();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public class Events implements Listener {
        
        @EventHandler
        public void onModelEngineGeneratorStart(ModelEngineInitializeEvent e) {
        EntityTypeRegistry.newRegistry()
                .register(greatHunger)
                .apply();
        
            getServer().getPluginManager().registerEvents(new ScalaEvents(EntitiesLauncher.this), EntitiesLauncher.this);
        }
    }

}
