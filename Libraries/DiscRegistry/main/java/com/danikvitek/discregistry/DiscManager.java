package com.danikvitek.discregistry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DiscManager implements Listener {
    DiscRegistry discRegistry;

    public DiscManager(DiscRegistry discRegistry) {
        this.discRegistry = discRegistry;
    }

    @EventHandler
    public void onDiscInJukebox(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null &&
            event.getClickedBlock().getBlockData() instanceof Jukebox) {
            if (((Jukebox) event.getClickedBlock().getBlockData()).hasRecord()) {
//                for (Player player : event.getClickedBlock().getWorld().getPlayers())
//                    player.stop(, SoundCategory.RECORDS);
            }
            else {
                try {
                    Object jukeBoxTileEntity = discRegistry.getReflector().getTileEntityJukeBox(event.getClickedBlock().getLocation());
                    discRegistry.getReflector().tileEntityJukeBox_setRecord(
                            event.getPlayer(),
                            jukeBoxTileEntity,
                            new ItemStack(Material.PAPER)
                    );
                    // todo: interaction
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
