package org.foton.utils.nms;

import org.bukkit.Bukkit;

@Deprecated(forRemoval = true)
public class Reflector_1_8 extends Reflector {
    public Reflector_1_8() throws ClassNotFoundException {
        String namespace = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        this.TileEntityJukeBox = Class.forName("net.minecraft.server." + namespace + "."); // todo
        this.PlayerConnection = Class.forName("net.minecraft.server." + namespace + ".PlayerConnection");
        this.playerConnectionField = "playerConnection";
    }
}