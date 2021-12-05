package org.foton.entities;

import com.ticxo.modelengine.api.event.ModelEngineInitializeEvent;
import io.denery.entityregistry.EntityTypeRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.foton.architecture.Launcher;
import org.foton.entities.commands.Commands;
import org.foton.entities.mobs.GreatHunger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public final class EntitiesLauncher extends JavaPlugin implements Launcher {
    public static final Logger logger = LoggerFactory.getLogger("Foton-Entities");

    @Override
    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void loadCommands() {
        Objects.requireNonNull(getCommand("erspawn")).setExecutor(Commands.getInstance());
    }

    @Override
    public void onEnable() {
        logger.info("Loading Entities Module.");
        logger.info("Version dev-1.0.019");
        
        logger.info("Registered entities: " + Arrays.toString(EntityTypeRegistry.getInstance().getRegisteredEntities().keySet().toArray()));

        loadEvents();
        loadCommands();
    }

    @Override
    public void onDisable() {
        logger.info("Shutting down entities module...");
    }
    /*
    private class ScalaSupport implements Listener {
        @EventHandler
        public void scalaSupportInitEvent(ScalaSupportInitEvent e) {
            EntityTypeRegistry.newRegistry()
                    .register(greatHunger)
                    .apply();
            getServer().getPluginManager().registerEvents(new Events(), EntitiesLauncher.this);
        }
    }
    */
    public class Events implements Listener {
        @EventHandler
        public void onModelEngineGeneratorStart(ModelEngineInitializeEvent e) {
            
            EntityTypeRegistry.newRegistry()
                    .register(GreatHunger.greatHungerType())
                    .apply();
            
            getServer().getPluginManager().registerEvents(new ScalaEvents(EntitiesLauncher.this), EntitiesLauncher.this);
        }
    }

}
