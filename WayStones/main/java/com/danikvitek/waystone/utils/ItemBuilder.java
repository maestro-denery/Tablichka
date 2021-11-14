package com.danikvitek.waystone.utils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {
    private ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String ...lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addNBT(String name, NBTBase tag) {
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(this.itemStack);
        NBTTagCompound compound = nmsItemStack.hasTag() ? nmsItemStack.getTag() : new NBTTagCompound();
        assert compound != null;
        compound.set(name, tag);
        nmsItemStack.setTag(compound);
        this.itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
