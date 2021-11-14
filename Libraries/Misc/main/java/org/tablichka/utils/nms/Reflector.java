package org.tablichka.utils.nms;

import net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayOutNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Reflector {
    protected Class<?> CraftItemStack;
    protected Class<?> CraftWorld;
    protected Class<?> CraftPlayer;

    protected Class<?> ItemStack;
    protected Class<?> TileEntity;
    protected Class<?> TileEntityJukeBox;
    protected Class<?> BlockJukeBox;
    protected Class<?> World;
    protected Class<?> BlockPosition;
    protected Class<?> IBlockData;

    protected Class<?> Packet;
    protected Class<?> PacketPlayOutBlockChange;
    protected Class<?> PacketPlayInTileNBTQuery;
    protected Class<?> PacketPlayOutNBTQuery;

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
            if ((boolean) this.ItemStack.getDeclaredMethod("hasTag").invoke(nmsItemStack))
                this.NBTTagCompound
                        .getDeclaredMethod("set", String.class, this.NBTBase)
                        .invoke(nbtItem, "tag", this.ItemStack.getDeclaredMethod("getTag").invoke(nmsItemStack));
            this.NBTTagCompound
                    .getDeclaredMethod("setByte", String.class, byte.class)
                    .invoke(nbtItem, "Count", (byte) itemStack.getAmount());
            return nbtItem;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object itemStackToNMS(ItemStack itemStack) {
        try {
            return this.CraftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void tileEntityJukeBox_setRecord(Player player, Object jukeBox, ItemStack recordItem) throws IllegalArgumentException {
        if (!jukeBox.getClass().equals(this.TileEntityJukeBox))
            throw new IllegalArgumentException("jukeBox must be of class TileEntityJukeBox, but was " + jukeBox.getClass());
        try {
            this.TileEntityJukeBox.getDeclaredMethod("setRecord", this.ItemStack)
                    .invoke(jukeBox, itemStackToNMS(recordItem)); // put record item in jukebox

            Object position = this.TileEntity.getDeclaredMethod("getPosition").invoke(jukeBox);
            Object nbt = this.TileEntityJukeBox.getDeclaredMethod("save", this.NBTTagCompound)
                    .invoke(jukeBox, this.NBTTagCompound.getConstructor().newInstance());

            int transactionId = new Random().nextInt();

//            PacketPlayOutTileEntityData
//            Object tileNBTQueryPacket = this.PacketPlayInTileNBTQuery.getConstructor(int.class, this.BlockPosition)
//                    .newInstance(transactionId, position);
            Object NBTQueryResponsePacket = this.PacketPlayOutNBTQuery.getConstructor(int.class, this.NBTTagCompound)
                    .newInstance(transactionId, nbt);

//            Object blockChangePacket = this.PacketPlayOutBlockChange.getConstructor(this.BlockPosition, this.IBlockData)
//                    .newInstance(position, iBlockData);
            Object entityPlayer = this.CraftPlayer.getDeclaredMethod("getHandle")
                    .invoke(this.CraftPlayer.cast(player));
            Object playerConnection = entityPlayer.getClass().getDeclaredField(this.playerConnectionField)
                    .get(entityPlayer);

//            playerConnection.getClass().getDeclaredMethod("sendPacket", this.Packet)
//                    .invoke(playerConnection, tileNBTQueryPacket);
            playerConnection.getClass().getDeclaredMethod("sendPacket", this.Packet)
                    .invoke(playerConnection, NBTQueryResponsePacket);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public Object getTileEntityJukeBox(@NotNull Location location) throws RuntimeException {
        Object craftWorld = this.CraftWorld.cast(location.getWorld());
        try {
            Object WorldServer = this.CraftWorld.getDeclaredMethod("getHandle").invoke(craftWorld);
            Object tileEntity = this.World
                    .getDeclaredMethod("getTileEntity", this.BlockPosition)
                    .invoke(
                            WorldServer,
                            this.BlockPosition.getConstructor(int.class, int.class, int.class)
                                    .newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ())
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