package com.danikvitek.waystone.misc.utils.gui;

import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class Menu {
    private final TexturedInventoryWrapper inventoryWrapper;
    private final HashMap<Integer, Button> buttons;

    public Menu(TexturedInventoryWrapper inventoryWrapper) {
        this.inventoryWrapper = inventoryWrapper;
        buttons = new HashMap<>();
    }

    public void setButton(int slot, Button button) {
        buttons.put(slot, button);
    }

    public void performClick(Menu menu, InventoryClickEvent event) {
        if (buttons.get(event.getSlot()) != null)
            buttons.get(event.getSlot()).onClick(menu, event);
    }

    protected void loadButtons() {
        Inventory inventory = inventoryWrapper.getInternal();
        buttons.forEach(inventory::setItem);
    }

    public void open(Player player) {
        loadButtons();
        inventoryWrapper.showInventory(player);
    }

    public TexturedInventoryWrapper getInventoryWrapper() {
        return inventoryWrapper;
    }
}
