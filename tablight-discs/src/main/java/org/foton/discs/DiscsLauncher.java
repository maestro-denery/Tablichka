package org.foton.discs;

import com.danikvitek.discregistry.DiscManager;
import com.danikvitek.discregistry.DiscRegistry;
import org.foton.utils.nms.Reflector_1_17;
import org.foton.utils.nms.Reflector_1_8;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static com.danikvitek.discregistry.DiscRegistry.log;

public class DiscsLauncher extends JavaPlugin {
    private final DiscRegistry discRegistry = new DiscRegistry(this);

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
		
		Bukkit.getPluginManager().registerEvents(new DiscManager(discRegistry), this);

        try {
            discRegistry.setReflector(new Reflector_1_8());
        } catch (ClassNotFoundException | NoClassDefFoundError e1) {
            try {
                log("Version is greater than 1.16.5");
                discRegistry.setReflector(new Reflector_1_17());
            } catch (ClassNotFoundException | NoClassDefFoundError e2) {
                e2.printStackTrace();
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
}
