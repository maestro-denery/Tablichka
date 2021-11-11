package org.tablichka.architecture;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Bootstrap {
    protected JavaPlugin plugin;
    public Bootstrap(JavaPlugin plugin) {
        loadCommands();
        loadEvents();
        this.plugin = plugin;
    }

    public abstract void bootstrap();

    protected abstract void loadEvents();

    protected abstract void loadCommands();
}
