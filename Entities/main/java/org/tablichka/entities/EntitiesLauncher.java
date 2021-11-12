package org.tablichka.entities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tablichka.architecture.Launcher;
import org.tablichka.entities.commands.EntitiesCommands;

import java.util.Objects;

public final class EntitiesLauncher extends JavaPlugin implements Launcher {
    public static final Logger logger = LoggerFactory.getLogger("Tablichka-Entities");


    @Override
    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void loadCommands() {
        Objects.requireNonNull(getCommand("ERSpawn")).setExecutor(EntitiesCommands.getInstance());
    }

    @Override
    public void onEnable() {
        logger.info("Loading Entities Module.");
        loadEvents();
        loadCommands();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static class Events implements Listener {

        @EventHandler
        public static void onCreatureSpawn(CreatureSpawnEvent e) {
            LivingEntity entity = e.getEntity();
            //EntityRegistry.newRegistry().register()
        }
    }

}
