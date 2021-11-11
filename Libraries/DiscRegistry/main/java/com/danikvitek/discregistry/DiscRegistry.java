package com.danikvitek.discregistry;

import com.danikvitek.discregistry.utils.nms.Reflector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DiscRegistry {
    private static JavaPlugin plugin;
    public DiscRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

//    private DatabaseManager dbManager;

    private Reflector reflector;

    private static final Logger logger = Bukkit.getLogger();

//    public DatabaseManager getDbManager() {
//        return dbManager;
//    }

    public static void log(String message) {
        logger.info(String.format("[%s] %s", plugin.getName(), message));
    }

    public Reflector getReflector() {
        return reflector;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
    }
}
