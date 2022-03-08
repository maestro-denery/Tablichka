package org.foton.utils.nms;

@Deprecated(forRemoval = true)
public class Reflector_1_17 extends Reflector {
    public Reflector_1_17() throws ClassNotFoundException {
        this.CraftItemStack = Class.forName("org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack");
        this.CraftWorld = Class.forName("org.bukkit.craftbukkit.v1_17_R1.CraftWorld");
        this.CraftPlayer = Class.forName("org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer");

        this.ItemStack = Class.forName("net.minecraft.world.item.ItemStack");
        this.TileEntity = Class.forName("net.minecraft.world.level.block.entity.TileEntity");
        this.TileEntityJukeBox = Class.forName("net.minecraft.world.level.block.entity.TileEntityJukeBox");
        this.BlockJukeBox = Class.forName("net.minecraft.world.level.block.BlockJukeBox");
        this.World = Class.forName("net.minecraft.world.level.World");
        this.BlockPosition = Class.forName("net.minecraft.core.BlockPosition");
        this.IBlockData = Class.forName("net.minecraft.world.level.block.state.IBlockData");

        this.Packet = Class.forName("net.minecraft.network.protocol.Packet");
        this.PacketPlayOutBlockChange = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutBlockChange");
        this.PacketPlayInTileNBTQuery = Class.forName("net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery");
        this.PacketPlayOutNBTQuery = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutNBTQuery");

        this.NBTBase = Class.forName("net.minecraft.nbt.NBTBase");
        this.NBTTagCompound = Class.forName("net.minecraft.nbt.NBTTagCompound");

        this.PlayerConnection = Class.forName("net.minecraft.server.network.PlayerConnection");
        this.playerConnectionField = "b";
    }
}