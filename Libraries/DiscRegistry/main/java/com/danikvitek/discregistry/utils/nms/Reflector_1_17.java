package com.danikvitek.discregistry.utils.nms;

public class Reflector_1_17 extends Reflector {
    public Reflector_1_17() throws ClassNotFoundException {
        this.CraftItemStack = Class.forName("org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack");
        this.ItemStackNMS = Class.forName("net.minecraft.world.item.ItemStack");

        this.TileEntityJukeBox = Class.forName("net.minecraft.world.level.block.entity.TileEntityJukeBox");
        this.BlockJukeBox = Class.forName("net.minecraft.world.level.block.BlockJukeBox");
        this.CraftWorld = Class.forName("org.bukkit.craftbukkit.v1_17_R1.CraftWorld");
        this.BlockPosition = Class.forName("net.minecraft.core.BlockPosition");

        this.NBTBase = Class.forName("net.minecraft.nbt.NBTBase");
        this.NBTTagCompound = Class.forName("net.minecraft.nbt.NBTTagCompound");

        this.PlayerConnection = Class.forName("net.minecraft.server.network.PlayerConnection");
        this.playerConnectionField = "b";
    }
}