package com.danikvitek.waystone.utils;

import net.minecraft.nbt.NBTBase;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class NBTManager {
    private final net.minecraft.world.item.ItemStack nmsItemStack;

    public NBTManager(@NotNull ItemStack itemStack) throws IllegalArgumentException {
        net.minecraft.world.item.ItemStack newNMSmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        if (newNMSmsItemStack.hasTag())
            this.nmsItemStack = newNMSmsItemStack;
        else throw new IllegalArgumentException("This ItemStack does not have NTB tags");
    }

    public @Nullable NBTBase getTag(String name) {
        assert nmsItemStack.getTag() != null;
        return nmsItemStack.getTag().get(name);
    }
}
