package com.danikvitek.discregistry.utils.nms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class Reflector {
    protected Class<?> CraftItemStack;
    protected Class<?> ItemStackNMS;

    protected Class<?> TileEntityJukeBox;
    protected Class<?> BlockJukeBox;
    protected Class<?> CraftWorld;
    protected Class<?> BlockPosition;

    protected Class<?> NBTBase;
    protected Class<?> NBTTagCompound;

    protected Class<?> PlayerConnection;
    protected String playerConnectionField;


    public Reflector() {
    }

    public Object addRecordToCompound(Object compound, ItemStack recordItem) throws IllegalArgumentException {
        try {
            if (compound.getClass().equals(this.NBTTagCompound))
                compound.getClass()
                        .getDeclaredMethod("set", String.class, this.NBTBase)
                        .invoke(compound, "RecordItem", itemStackToNBT(recordItem));
            else
                throw new IllegalArgumentException(
                        "Arguments must both be NBTTagCompound class instances." +
                        "\ncompound class: " + compound.getClass());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return compound;
    }

    public @Nullable Object itemStackToNBT(ItemStack itemStack) {
        try {
            Object nbtItem = this.NBTTagCompound.getDeclaredConstructor().newInstance();
            this.NBTTagCompound
                    .getDeclaredMethod("setString", String.class, String.class)
                    .invoke(nbtItem, "id", itemStack.getType().getKey().toString());
            Object nmsItemStack = this.CraftItemStack
                    .getDeclaredMethod("asNMSCopy", itemStack.getClass())
                    .invoke(itemStack, itemStack);
            if ((boolean) this.ItemStackNMS.getDeclaredMethod("hasTag").invoke(nmsItemStack))
                this.NBTTagCompound
                        .getDeclaredMethod("set", String.class, this.NBTBase)
                        .invoke(nbtItem, "tag", this.ItemStackNMS.getDeclaredMethod("getTag").invoke(nmsItemStack));
            this.NBTTagCompound
                    .getDeclaredMethod("setByte", String.class, byte.class)
                    .invoke(nbtItem, "Count", (byte) itemStack.getAmount());
            return nbtItem;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void tileEntityJukeBox_load(Object jukeBox, Object record) throws IllegalArgumentException {
        if (!jukeBox.getClass().equals(this.TileEntityJukeBox))
            throw new IllegalArgumentException("jukeBox must be of class TileEntityJukeBox, but was " + jukeBox.getClass());
        if (!record.getClass().equals(this.NBTTagCompound))
            throw new IllegalArgumentException("record must be of class NBTTagCompound, but was " + record.getClass());
        try {
            this.TileEntityJukeBox.getDeclaredMethod("load", this.ItemStackNMS).invoke(jukeBox, record);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Object getTileEntityJukeBox(@NotNull Location location) throws RuntimeException {
        Object craftWorld = this.CraftWorld.cast(location.getWorld());
        try {
            Object nmsWorld = this.CraftWorld.getDeclaredMethod("getHandle").invoke(craftWorld);
            Object tileEntity = nmsWorld.getClass()
                    .getDeclaredMethod("getTileEntity", this.BlockPosition)
                    .invoke(
                            nmsWorld,
                            this.BlockPosition.getConstructor(int.class, int.class, int.class)
                                    .newInstance(location.getBlockX(), location.getBlockY(), location.getBlockY())
                    );
            if (tileEntity.getClass().equals(this.TileEntityJukeBox))
                return this.TileEntityJukeBox.cast(tileEntity);
            else
                throw new RuntimeException("The block appeared to not be a jukebox");
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}