package org.tablichka.discs;

import com.danikvitek.discregistry.DiscManager;
import com.danikvitek.discregistry.DiscRegistry;
import com.danikvitek.discregistry.utils.nms.Reflector_1_17;
import com.danikvitek.discregistry.utils.nms.Reflector_1_8;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.tablichka.architecture.Bootstrap;

import static com.danikvitek.discregistry.DiscRegistry.log;

public class DiscsBootstrap extends Bootstrap {
    private DiscRegistry discRegistry = new DiscRegistry(plugin);
    public DiscsBootstrap(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void bootstrap() {

        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();

//        dbManager = new DatabaseManager(
//                getConfig().getString("database.url"),
//                getConfig().getString("database.user"),
//                getConfig().getString("database.password")
//        );

        try {
            discRegistry.setReflector(new Reflector_1_8());
        } catch (ClassNotFoundException | NoClassDefFoundError e1) {
            try {
                log(e1.getMessage());
                discRegistry.setReflector(new Reflector_1_17());
            } catch (ClassNotFoundException | NoClassDefFoundError e2) {
                log(e2.getMessage());
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Не удалось загрузить плагин для этой версии (" + Bukkit.getVersion() + ")");
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
        }

        new DiscManager(discRegistry);

        log("Loading completed");
    }


    @Override
    protected void loadEvents() {
        Bukkit.getPluginManager().registerEvents(new DiscManager(discRegistry), plugin);
    }

    @Override
    protected void loadCommands() {

    }
}
