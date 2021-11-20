package com.danikvitek.waystone.misc.utils.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class Button extends ItemStack {
    /**
     * Creates a button with specified Material
     * @param material button's ItemStack type
     */
    public Button(@Nullable Material material) {
        super(material != null ? material : Material.AIR);
    }

    /**
     * Creates a button with specified ItemStack
     * @param item button's ItemStack
     */
    public Button(@Nullable ItemStack item) {
        super(item != null ? item : new ItemStack(Material.AIR));
    }

    /**
     * Creates a button with specified ItemStack type, name and lore
     * @param material button's ItemStack type
     * @param name     button's ItemStack name
     * @param lore     button's ItemStack lore
     */
    public Button(@NotNull Material material, String name, String... lore) {
        this(material);
        ItemMeta meta = getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        for (int i = 0; i < lore.length; i++) lore[i] = ChatColor.translateAlternateColorCodes('&', lore[i]);

        meta.setLore(Arrays.asList(lore));
        setItemMeta(meta);
    }

    /**
     * Button's click handler
     * @param menu  current button's menu
     * @param event click event that was called
     */
    public abstract void onClick(Menu menu, InventoryClickEvent event);
}
