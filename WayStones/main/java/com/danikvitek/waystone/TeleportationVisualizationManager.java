package com.danikvitek.waystone;

public class TeleportationVisualizationManager {
    private static TeleportationVisualizationManager instance;
    private static boolean isInitialized = false;
    private WayStonesPlugin plugin;

    private TeleportationVisualizationManager() {}

    public static TeleportationVisualizationManager getInstance() {
        if (instance == null) instance = new TeleportationVisualizationManager();
        return instance;
    }

    public void init(WayStonesPlugin plugin) {
        if (!isInitialized) {
            this.plugin = plugin;
            isInitialized = true;
        }
        else throw new IllegalStateException("TeleportationVisualizationManager is already initialized");
    }


}
