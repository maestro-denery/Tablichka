package com.danikvitek.waystone.misc.utils;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class NBTManager {
    private final ItemStack itemStack;
    private final NbtCompound compound;

    public NBTManager(@NotNull ItemStack itemStack) {
        this.itemStack = MinecraftReflection.getBukkitItemStack(itemStack);
        this.compound = (NbtCompound) NbtFactory.fromItemTag(this.itemStack);
    }

    public NBTManager addTag(NbtBase<?> tag) {
        compound.put(tag);
        return this;
    }

    public ItemStack build() {
        NbtFactory.setItemTag(itemStack, compound);
        return itemStack;
    }

    public Optional<NbtBase<?>> getTag(String name) {
        return Optional.ofNullable(compound.getValue(name));
    }
}
