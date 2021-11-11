package org.tablichka.entities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tablichka.architecture.Bootstrap;
import org.tablichka.entities.commands.EntitiesCommands;

import java.util.Objects;

public class EntitiesBootstrap extends Bootstrap {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka-Entities");
    //private Behaviour behaviour = new Behaviour()
    public EntitiesBootstrap(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void bootstrap() {
        logger.info("Loading Entities Module.");
    }

    @Override
    public void loadEvents() {
        plugin.getServer().getPluginManager().registerEvents(new Events(), plugin);
    }

    @Override
    protected void loadCommands() {
        Objects.requireNonNull(plugin.getCommand("ERSpawn")).setExecutor(EntitiesCommands.getInstance());
    }

    public static class Events implements Listener {

        @EventHandler
        public static void onCreatureSpawn(CreatureSpawnEvent e) {
            LivingEntity entity = e.getEntity();
            //EntityRegistry.newRegistry().register()
        }
    }

}
