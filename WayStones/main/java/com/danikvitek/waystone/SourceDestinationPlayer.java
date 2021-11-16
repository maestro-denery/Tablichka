package com.danikvitek.waystone;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SourceDestinationPlayer implements Listener {
    private static final String screenEffectColor = "#101010";
    private static final long screenEffectFadeIn = 20L;
    private static final long screenEffectStay = 20L;
    private static final long screenEffectFadeOut = 20L;
    private static final boolean screenEffectFreeze = true;

    public static void registerNewPair(@NotNull Player player, @NotNull Waystone source, @NotNull Waystone destination) {
        Bukkit.getPluginManager().registerEvents(
                new SourceDestinationPlayer(source, destination, player),
                Main.getPlugin(Main.class)
        );
    }

    @NotNull private Waystone source;
    @NotNull private Waystone destination;
    @NotNull private final Player player;
    @NotNull private final BukkitTask drawFieldTask;

    public SourceDestinationPlayer(@NotNull Waystone source, @NotNull Waystone destination, @NotNull Player player) {
        this.source = source;
        this.destination = destination;
        this.player = player;
        this.drawFieldTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (double z = -90D; z < 90D; z+=9D) {
                    double yIncrement = 350D/8100D * z * z + 10D; // Math.abs(359D / 90D * z) + 1D;
                    for (double y = 0D; y <= 360D; y += yIncrement) {
                        player.spawnParticle(
                                Particle.REDSTONE,
                                toSourceLocation().add(
                                        new Vector(1, 0, 0)
                                                .rotateAroundZ(z) // vertically
                                                .rotateAroundY(y) // horizontally
                                                .multiply(5)),
                                1,
                                new Particle.DustOptions(Color.SILVER, 0.5f)
                        );
                    }
                }
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 0L, 10L);
    }

    @EventHandler
    public void onCrossingBorderLine(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.equals(this.player)) {
            if (!player.getWorld().equals(this.source.getWorld())) {
                HandlerList.unregisterAll(this);
                this.stopDrawFieldTask();
            } else if (this.toSourceLocation().distance(player.getLocation()) > 5) {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        String.format(
                                "screeneffect fullscreen %s %d %d %d %s %s",
                                screenEffectColor,
                                screenEffectFadeIn,
                                screenEffectStay,
                                screenEffectFadeOut,
                                screenEffectFreeze ? "freeze" : "nofreeze",
                                player.getName()
                        ));
                SourceDestinationPlayer thisPair = this;
                Location offset = player.getLocation().subtract(this.toSourceLocation());
                Location destinationLocation = this.toDestinationLocation().add(offset.toVector());
                destinationLocation.setDirection(player.getLocation().getDirection());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(destinationLocation);
                        HandlerList.unregisterAll(thisPair);
                        thisPair.stopDrawFieldTask();
                    }
                }.runTaskLater(Main.getPlugin(Main.class), screenEffectFadeIn);
            }
        }
    }

    public @NotNull Waystone getSource() {
        return source;
    }

    public @NotNull Waystone getDestination() {
        return destination;
    }

    private void stopDrawFieldTask() {
        if (!this.drawFieldTask.isCancelled())
            this.drawFieldTask.cancel();
    }

    public @NotNull Location toSourceLocation() {
        return new Location(
                source.getWorld(),
                source.getX() + 0.5D,
                source.getY(),
                source.getZ() + 0.5D
        );
    }

    public @NotNull Location toDestinationLocation() {
        return new Location(
                destination.getWorld(),
                destination.getX() + 0.5D,
                destination.getY(),
                destination.getZ() + 0.5D
        );
    }

    public void setSource(@NotNull Waystone source) {
        this.source = source;
    }

    public void setDestination(@NotNull Waystone destination) {
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceDestinationPlayer that)) return false;
        return getSource().equals(that.getSource()) && getDestination().equals(that.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination());
    }
}
