package com.danikvitek.waystones;

import com.danikvitek.waystones.visualisation.TeleportVisManager;
import com.danikvitek.waystones.waystone.Waystone;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

public final class SourceDestinationPair implements Listener {
    private static final Map<Player, SourceDestinationPair> activeSDPs = new HashMap<>();

    private static final List<Vector[]> particleOffset;

    static {
        particleOffset = new ArrayList<>();
        double multiplier = 5d; // sphere radius
        for (double i = 0; i <= Math.PI; i += Math.PI / 20) {
            double radius = Math.sin(i) * multiplier;
            double y = Math.cos(i) * multiplier;
            int n_circle = i != 0 && i < Math.PI ? 20 : 1;
            Vector[] circle = new Vector[n_circle];
            int j = 0;
            for (double degree = 0d; degree < 360d; degree += 360d / n_circle) {
                double radians = Math.toRadians(degree);
                double x = Math.cos(radians) * radius;
                double z = Math.sin(radians) * radius;
                circle[j++] = new Vector(x, y, z);
            }
            particleOffset.add(circle);
        }
    }

    @NotNull private final Waystone source;
    @NotNull private final Waystone destination;
    @NotNull private final BukkitTask drawFieldTask;

    private SourceDestinationPair(@NotNull Waystone source, @NotNull Waystone destination, @NotNull Player player) {
        this.source = source;
        this.destination = destination;
        Location sourceLocation = toSourceLocation();
        this.drawFieldTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Vector[] circle : particleOffset) {
                    Stream.of(circle).forEach(
                            v -> player.spawnParticle(
                                    Particle.REDSTONE,
                                    sourceLocation.clone().add(v),
                                    1,                        // amount
                                    0.5, 1, 0.5,      // max offsets
                                    new Particle.DustOptions(Color.PURPLE, 1f)
                            )
                    );
                }
            }
        }.runTaskTimerAsynchronously(WayStonesPlugin.getPlugin(WayStonesPlugin.class), 0L, 5L);
    }

    public static boolean hasSelection(Player player) {
        return activeSDPs.containsKey(player);
    }

    public static void registerNewPair(@NotNull Player player, @NotNull Waystone source, @NotNull Waystone destination) {
        SourceDestinationPair sdp = new SourceDestinationPair(source, destination, player);
        if (hasSelection(player)) {
            stopAndClearByPlayer(player);
        }
        activeSDPs.put(player, sdp);
        Bukkit.getPluginManager().registerEvents(
                sdp,
                WayStonesPlugin.getPlugin(WayStonesPlugin.class)
        );
        TeleportVisManager.startVisualisation(player, sdp);
    }

    public static @Nullable SourceDestinationPair getByPlayer(Player player) {
        return activeSDPs.get(player);
    }

    public static void stopAndClearByPlayer(Player player) {
        SourceDestinationPair sdp = getByPlayer(player);
        if (sdp != null) {
            sdp.stopTeleportation();
            activeSDPs.remove(player);
            TeleportVisManager.cancelVisualisation(player, sdp);
        }
    }

    @EventHandler
    public void onMoveInTeleportationField(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (this.equals(activeSDPs.get(player))) {
            if (!player.getWorld().equals(this.source.world())) {
                stopAndClearByPlayer(player);
            } else {
                Location playerLocation = event.getTo(); // player.getLocation();
                Location destinationLocation = this.toDestinationLocation().add(playerLocation.clone().subtract(this.toSourceLocation()).toVector());
                if (this.toSourceLocation().distance(playerLocation) > 5) {
                    destinationLocation.setDirection(playerLocation.getDirection());
                    stopAndClearByPlayer(player);
                    Vector velocity = player.getVelocity();
                    player.teleport(destinationLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    player.setVelocity(velocity);
                } else {
                    destinationLocation.getWorld().spawnParticle(
                            Particle.REDSTONE,
                            destinationLocation.clone().add(0, 1, 0),
                            10,                             // amount
                            0.3, 1, 0.3,      // maximal offsets
                            new Particle.DustOptions(Color.PURPLE, 1f)
                    );
                }
            }
        }
    }

    private void stopTeleportation() {
        PlayerMoveEvent.getHandlerList().unregister(this);
        this.stopDrawFieldTask();
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
                source.world(),
                source.x() + 0.5D,
                source.y(),
                source.z() + 0.5D
        );
    }

    public @NotNull Location toDestinationLocation() {
        return new Location(
                destination.world(),
                destination.x() + 0.5D,
                destination.y(),
                destination.z() + 0.5D
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceDestinationPair that)) return false;
        return getSource().equals(that.getSource()) && getDestination().equals(that.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination());
    }

    public static class UnexpectedMovementListener implements Listener {
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            Player player = event.getEntity();
            stopAndClearByPlayer(player);
        }

        @EventHandler
        public void onPlayerTeleport(PlayerTeleportEvent event) {
            Player player = event.getPlayer();
            stopAndClearByPlayer(player);
        }

        @EventHandler
        public void onPlayerLogout(PlayerQuitEvent event) {
            Player player = event.getPlayer();
            stopAndClearByPlayer(player);
        }
    }
}
