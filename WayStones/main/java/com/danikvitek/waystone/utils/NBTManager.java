package com.danikvitek.waystone.utils;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class NBTManager {
    private final net.minecraft.world.item.ItemStack nmsItemStack;
    private final NBTTagCompound compound;

    public NBTManager(@NotNull ItemStack itemStack) throws IllegalArgumentException {
        this.nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        this.compound = this.nmsItemStack.hasTag() ? this.nmsItemStack.getTag() : new NBTTagCompound();
    }

    public NBTManager addTag(String name, NBTBase tag) {
        compound.set(name, tag);
        return this;
    }

    public ItemStack build() {
        nmsItemStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public @Nullable NBTBase getTag(String name) {
        assert nmsItemStack.getTag() != null;
        return nmsItemStack.getTag().get(name);
    }
}
