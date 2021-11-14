package org.tablichka.discs;

import com.danikvitek.discregistry.DiscManager;
import com.danikvitek.discregistry.DiscRegistry;
import org.tablichka.utils.nms.Reflector_1_17;
import org.tablichka.utils.nms.Reflector_1_8;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.tablichka.architecture.Launcher;

import static com.danikvitek.discregistry.DiscRegistry.log;

public class DiscsLauncher extends JavaPlugin implements Launcher {
    private final DiscRegistry discRegistry = new DiscRegistry(this);

    @Override
    public void onEnable() {
        loadEvents();
        loadCommands();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        try {
            discRegistry.setReflector(new Reflector_1_8());
        } catch (ClassNotFoundException | NoClassDefFoundError e1) {
            try {
                log(e1.getMessage());
                discRegistry.setReflector(new Reflector_1_17());
            } catch (ClassNotFoundException | NoClassDefFoundError e2) {
                log(e2.getMessage());
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Не удалось загрузить плагин для этой версии (" + Bukkit.getVersion() + ")");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }

        new DiscManager(discRegistry);

        log("Loading completed");
    }

    @Override
    public void onDisable() {
        log("Disabling plugin.");
    }

    @Override
    public void loadEvents() {
        Bukkit.getPluginManager().registerEvents(new DiscManager(discRegistry), this);
    }

    @Override
    public void loadCommands() {

    }
}
