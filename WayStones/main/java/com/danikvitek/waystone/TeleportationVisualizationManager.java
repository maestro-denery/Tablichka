package com.danikvitek.waystone;

public class TeleportationVisualizationManager {
    private static TeleportationVisualizationManager instance;

    private TeleportationVisualizationManager() {}

    public static TeleportationVisualizationManager getInstance() {
        if (instance == null) instance = new TeleportationVisualizationManager();
        return instance;
    }

    
}
