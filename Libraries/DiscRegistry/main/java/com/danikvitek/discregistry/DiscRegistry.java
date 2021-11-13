package com.danikvitek.discregistry;

import org.tablichka.utils.nms.Reflector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.tablichka.architecture.Applier;

import java.util.logging.Logger;

public final class DiscRegistry {
    private static JavaPlugin plugin;
    public DiscRegistry(JavaPlugin plugin) {
        DiscRegistry.plugin = plugin;
    }

    private Reflector reflector;

    private static final Logger logger = Bukkit.getLogger();

    public static void log(String message) {
        logger.info(String.format("[%s] %s", plugin.getName(), message));
    }

    public Reflector getReflector() {
        return reflector;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
    }

    public class DiscApplier implements Applier<DiscRegistry> {

        @Override
        public DiscRegistry apply() {
            return null;
        }
    }
}
