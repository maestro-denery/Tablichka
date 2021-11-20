package com.danikvitek.waystone;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.danikvitek.waystone.utils.*;
import com.danikvitek.waystone.utils.gui.*;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class WayStonesPlugin extends JavaPlugin implements Listener {

    private static final Logger logger = Bukkit.getLogger();

    ProtocolManager protocolManager;
    boolean isWaystonesListenerRegistered = false;

    // TODO: 09.11.2021 integrate CoreProtect

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        protocolManager = ProtocolLibrary.getProtocolManager();

        DatabaseManager.getInstance().init(
                getConfig().getString("database.host"),
                getConfig().getInt("database.port", 3306),
                getConfig().getString("database.name"),
                getConfig().getString("database.login"),
                getConfig().getString("database.password"),
                getConfig().getBoolean("database.create-database")
        );
        Bukkit.getPluginManager().registerEvents(this, this);
        log("Loading completed");
    }

    public static void log(String message) {
        logger.info(String.format("[%s] %s", WayStonesPlugin.getPlugin(WayStonesPlugin.class).getName(), message));
    }

    @EventHandler
    public void onIALoad(ItemsAdderLoadDataEvent event) {
        log("ItemsAdderLoadDataEvent fired");
        if (!isWaystonesListenerRegistered) {
            WaystoneManager.getInstance().init(this);
            Bukkit.getPluginManager().registerEvents(WaystoneManager.getInstance(), this);
            Bukkit.getPluginManager().registerEvents(MenuHandler.getListeners(), this);
            isWaystonesListenerRegistered = true;
            log("WayStones event listeners registered");
        }
    }
}
