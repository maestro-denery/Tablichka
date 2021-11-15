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

import java.io.IOException;
import java.util.logging.Logger;

public final class Main extends JavaPlugin implements Listener {

    private static final Logger logger = Bukkit.getLogger();

    ProtocolManager protocolManager;
    boolean isWaystonesListenerRegistered = false;

    // TODO: 09.11.2021 integrate CoreProtect

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        protocolManager = ProtocolLibrary.getProtocolManager();

        DatabaseManager.setDb_host(getConfig().getString("database.host"));
        DatabaseManager.setDb_port(getConfig().getInt("database.port"));
        DatabaseManager.setDb_name(getConfig().getString("database.name"));
        DatabaseManager.setDb_user(getConfig().getString("database.login"));
        DatabaseManager.setDb_password(getConfig().getString("database.password"));
        DatabaseManager.connectToDB();
        try {
            DatabaseManager.createTables();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public static void log(String message) {
        logger.info(String.format("[%s] %s", Main.getPlugin(Main.class).getName(), message));
    }

    @EventHandler
    public void onIALoad(ItemsAdderLoadDataEvent event) {
        if (!isWaystonesListenerRegistered) {
            Bukkit.getPluginManager().registerEvents(new WaystoneManager(this), this);
            Bukkit.getPluginManager().registerEvents(MenuHandler.getListeners(), this);
            isWaystonesListenerRegistered = true;
        }
    }
}
