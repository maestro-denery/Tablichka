package org.tablichka.architecture;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Bootstrap {
    protected JavaPlugin plugin;
    public Bootstrap(JavaPlugin plugin) {
        this.plugin = plugin;
        loadCommands();
        loadEvents();
    }

    public abstract void bootstrap();

    protected abstract void loadEvents();

    protected abstract void loadCommands();
}
