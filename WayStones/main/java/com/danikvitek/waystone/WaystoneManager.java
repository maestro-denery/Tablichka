package com.danikvitek.waystone;

import com.danikvitek.waystone.utils.*;
import com.danikvitek.waystone.utils.gui.Menu;
import com.danikvitek.waystone.utils.gui.MenuHandler;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.Events.CustomBlockInteractEvent;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tablichka.utils.Converter;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class WaystoneManager implements Listener {
    Plugin plugin;

    WaystoneManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public static final String topHalfID = "ingredients:platinum_block";
    @NotNull
    public static final String bottomHalfId = "ingredients:cobalt_block";
    @NotNull
    public static final String waystoneGiuId = "hub_furniture:jukebox_main";

    private static final Map<Player, Integer> playerPages = new HashMap<>();
    private static final Map<Player, SourceDestinationPair> playerSourceDestinationPairs = new HashMap<>();

    @EventHandler
    public void onWaystoneInteract(CustomBlockInteractEvent event) {
        if (!event.getPlayer().isSneaking()) {
            if (Objects.equals(event.getNamespacedID(), topHalfID)) {
                CustomBlock bottomHalf = CustomBlock.byAlreadyPlaced(event.getBlockClicked().getRelative(BlockFace.DOWN));
                if (bottomHalf != null && bottomHalf.getNamespacedID().equals(bottomHalfId)) {
                    int x = event.getBlockClicked().getX(),
                            y = event.getBlockClicked().getY() - 1,
                            z = event.getBlockClicked().getZ();
                    UUID world = event.getBlockClicked().getWorld().getUID();
                    interactWithWaystone(event.getPlayer(), x, y, z, world);
                }
            } else if (Objects.equals(event.getNamespacedID(), bottomHalfId)) {
                CustomBlock topHalf = CustomBlock.byAlreadyPlaced(event.getBlockClicked().getRelative(BlockFace.UP));
                if (topHalf != null && topHalf.getNamespacedID().equals(topHalfID)) {
                    int x = event.getBlockClicked().getX(),
                            y = event.getBlockClicked().getY(),
                            z = event.getBlockClicked().getZ();
                    UUID world = event.getBlockClicked().getWorld().getUID();
                    interactWithWaystone(event.getPlayer(), x, y, z, world);
                }
            }
        }
    }

    @EventHandler
    public void onWaystoneBreak(BlockBreakEvent event) {
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(event.getBlock());
        if (customBlock != null) {
            if (Objects.equals(customBlock.getNamespacedID(), topHalfID)) {
                CustomBlock bottomHalf = CustomBlock.byAlreadyPlaced(event.getBlock().getRelative(BlockFace.DOWN));
                if (bottomHalf != null && bottomHalf.getNamespacedID().equals(bottomHalfId)) {
                    int x = event.getBlock().getX(),
                            y = event.getBlock().getY() - 1,
                            z = event.getBlock().getZ();
                    UUID world = event.getBlock().getWorld().getUID();
                    breakWaystone(x, y, z, world);
                }
            } else if (Objects.equals(customBlock.getNamespacedID(), bottomHalfId)) {
                CustomBlock topHalf = CustomBlock.byAlreadyPlaced(event.getBlock().getRelative(BlockFace.UP));
                if (topHalf != null && topHalf.getNamespacedID().equals(topHalfID)) {
                    int x = event.getBlock().getX(),
                            y = event.getBlock().getY(),
                            z = event.getBlock().getZ();
                    UUID world = event.getBlock().getWorld().getUID();
                    breakWaystone(x, y, z, world);
                }
            }
        }
    }

    private void breakWaystone(int x, int y, int z, UUID world) {
        Integer waystoneId = getWaystoneID(x, y, z, world);
        if (waystoneId != null) {
            String waystoneName = DatabaseManager.makeExecuteQuery(
                    "select name from `waystones` where id = '" + waystoneId + "';",
                    null,
                    nameRS -> {
                        try {
                            if (nameRS.next())
                                return nameRS.getString(1);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        return null;
                    }
            );
            List<Player> players = DatabaseManager.makeExecuteQuery(
                    "select player from `player's waystones` where waystone_id = '" + waystoneId + "';",
                    null,
                    playersRS -> {
                        try {
                            List<UUID> players_ids = new ArrayList<>();
                            while (playersRS.next())
                                players_ids.add(Converter.uuidFromBytes(playersRS.getBytes(1)));
                            return Bukkit.getOnlinePlayers().stream().filter(p -> players_ids.contains(p.getUniqueId())).collect(Collectors.toList());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        return null;
                    }
            );
            DatabaseManager.makeExecuteUpdate(
                    "delete from `waystones` where id = '" + waystoneId + "';",
                    null);
            if (players != null && waystoneName != null)
                for (Player p : players)
                    p.sendMessage(String.format(ChatColor.RED + "Обелиск %s разрушен", waystoneName));
        }
    }

    private static void interactWithWaystone(@NotNull Player player, int x, int y, int z, @NotNull UUID world) {
        Integer existingId = getWaystoneID(x, y, z, world);
        Map<Integer, byte[]> values = new HashMap<>();
        values.put(1, Converter.uuidToBytes(player.getUniqueId()));
        if (existingId != null) {
            Optional<Boolean> knownWaystones = Optional.ofNullable(DatabaseManager.makeExecuteQuery(
                    "select count(1) from `player's waystones` where " +
                            "player = ? and waystone_id = '" + existingId + "';",
                    values,
                    rs -> {
                        try {
                            if (rs.next())
                                return rs.getInt(1) > 0;
                            else
                                return null;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
            ));
            if (knownWaystones.isEmpty()) throw new RuntimeException("Somehow database couldn't find a player-waystone pair!");

            if (knownWaystones.get()) {
                try {
                    openWaystoneGUI(player, existingId, x, y, z, world);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
            else { // if player does not know the waystone
                registerWaystoneForPlayer(player, x, y, z, world, existingId);
                try {
                    openWaystoneGUI(player, existingId, x, y, z, world);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
        else // if waystone is not registered
            registerWaystone(player, x, y, z, world);
    }

    private static void registerWaystoneForPlayer(@NotNull Player player, int x, int y, int z, @NotNull UUID world, int existingId) {
        if (player.getWorld().getUID().equals(world) && player.getLocation().toVector().distance(new Vector(x, y, z)) <= 6) {
            HashMap<Integer, byte[]> values = new HashMap<>();
            values.put(1, Converter.uuidToBytes(player.getUniqueId()));
            DatabaseManager.makeExecuteUpdate(
                    "insert into `player's waystones` values (?, '" + existingId + "');",
                    values);
            player.sendMessage(ChatColor.GOLD + "Обелиск сохранён");
            Main.log(player.getName() + " discovered waystone " + existingId);
        }
        else throwCantReachWaystoneException(player, x, y, z, world);
    }

    private static void registerWaystone(@NotNull Player player, int x, int y, int z, @NotNull UUID world) {
        if (player.getWorld().getUID().equals(world) && player.getLocation().toVector().distance(new Vector(x, y, z)) <= 6) {
            new AnvilGUI.Builder()
                    .text("Введите имя")
                    .itemLeft(new ItemBuilder(Material.PAPER)
                            .setName(ChatColor.GOLD + "Назовите путеводный камень")
                            .setLore(
                                    "",
                                    ChatColor.YELLOW + "максимум 50 символов"
                            )
                            .build())
                    .onComplete((p, t) -> {
                        HashMap<Integer, byte[]> values = new HashMap<>();
                        values.put(1, Converter.uuidToBytes(world));
                        DatabaseManager.makeExecuteUpdate(
                                "insert into `waystones` (x, y, z, world, name) values (" +
                                        "'" + x + "', '" + y + "', '" + z + "', ?, '" + t + "');",
                                values
                        );
                        p.sendMessage(ChatColor.GOLD + "Обелиск сформирован");
                        return AnvilGUI.Response.close();
                    })
                    .plugin(Main.getPlugin(Main.class))
                    .open(player);
            Integer existingId = getWaystoneID(x, y, z, world);
            if (existingId != null) {
                registerWaystoneForPlayer(player, x, y, z, world, existingId);
                try {
                    openWaystoneGUI(player, existingId, x, y, z, world);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
        else throwCantReachWaystoneException(player, x, y, z, world);
    }

    private static @Nullable Integer getWaystoneID(int x, int y, int z, @NotNull UUID world) {
        HashMap<Integer, byte[]> values = new HashMap<>();
        values.put(1, Converter.uuidToBytes(world));
        return DatabaseManager.makeExecuteQuery(
                "select id from `waystones` where " +
                        "x = '" + x + "' and y = '" + y + "' and z = '" + z + "' and world = ?;",
                values,
                idResultSet -> {
                    try {
                        if (idResultSet.next()) {
                            return idResultSet.getInt(1);
                        }
                        else return null;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        );
    }

    private static @Nullable String getWaystoneName(int waystoneId) {
        return DatabaseManager.makeExecuteQuery(
                "select name from `waystones` where id = '" + waystoneId + "';",
                null,
                nameResultSet -> {
                    try {
                        if (nameResultSet.next())
                            return nameResultSet.getString(1);
                        else
                            return null;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        return null;
                    }
                }
        );
    }

    private static void openWaystoneGUI(@NotNull Player player, int waystoneId, int x, int y, int z, @NotNull UUID world)
            throws IllegalStateException {
        if (player.getWorld().getUID().equals(world) && player.getLocation().toVector().distance(new Vector(x, y, z)) <= 6) {
            String waystoneName = getWaystoneName(waystoneId);
            if (waystoneName != null) {
                TexturedInventoryWrapper gui = new TexturedInventoryWrapper(
                        null,
                        18,
                        "Обелиск " + waystoneName,
                        new FontImageWrapper(waystoneGiuId)
                );
                Menu waystoneMenu = new Menu(gui);
                playerPages.put(player, 0);
                redrawWaystones(player, waystoneMenu, new Waystone(x, y, z, waystoneName, Bukkit.getWorld(world)));
                MenuHandler.openMenu(player, waystoneMenu);
            }
        }
        else throwCantReachWaystoneException(player, x, y, z, world);
    }

    private static void redrawWaystones(@NotNull Player player, Menu waystoneMenu, Waystone thisWaystone) {
        List<ItemStack> knownWaystones = getKnownWaystones(player);
        setWaystonesIcons(waystoneMenu, knownWaystones, thisWaystone);
        setControlButtons(player, waystoneMenu, thisWaystone);
    }

    private static void setControlButtons(@NotNull Player player, Menu waystoneMenu, Waystone thisWaystone) {
        waystoneMenu.setButton(9, new Button(Material.PAPER) { // todo: put left arrow button
            @Override
            public void onClick(Menu menu, InventoryClickEvent event) {
                event.setCancelled(true);
                if (playerPages.get(player) > 0) {
                    playerPages.put(player, playerPages.get(player) - 1);
                    List<ItemStack> newKnownWaystones = getKnownWaystones(player, thisWaystone);
                    while (playerPages.get(player) > 1 && newKnownWaystones.size() == 0) {
                        playerPages.put(player, playerPages.get(player) - 1);
                        newKnownWaystones = getKnownWaystones(player, thisWaystone);
                    }
                    setWaystonesIcons(menu, newKnownWaystones, thisWaystone);
                    MenuHandler.reloadMenu(player);
                }
            }
        });
        waystoneMenu.setButton(17, new Button(Material.STONE) { // todo: put right arrow button
            @Override
            public void onClick(Menu menu, InventoryClickEvent event) {
                event.setCancelled(true);
                playerPages.put(player, playerPages.get(player) + 1);
                List<ItemStack> newKnownWaystones = getKnownWaystones(player, thisWaystone);
                if (newKnownWaystones.size() == 0) {
                    playerPages.put(player, playerPages.get(player) - 1);
                    newKnownWaystones = getKnownWaystones(player, thisWaystone);
                }
                setWaystonesIcons(menu, newKnownWaystones, thisWaystone);
                MenuHandler.reloadMenu(player);
            }
        });
        waystoneMenu.setButton(13, new Button(Material.OAK_PLANKS) { // todo: put accept button
            @Override
            public void onClick(Menu menu, InventoryClickEvent event) {
                event.setCancelled(true);
                // todo: implement accept action
            }
        });
    }

    private static void setWaystonesIcons(Menu waystoneMenu, List<ItemStack> knownWaystones, Waystone thisWaystone) {
        for (int i = 0; i < 9; i++) {
            int _i = i;
            waystoneMenu.setButton(i, new Button(_i < knownWaystones.size() ? knownWaystones.get(_i) : null) {
                @Override
                public void onClick(Menu menu, InventoryClickEvent event) {
                    event.setCancelled(true);
                    if (_i < knownWaystones.size()) {
                        // TODO: 10.11.2021 implement selection action
                        NBTTagCompound dstTag = (NBTTagCompound) new NBTManager(knownWaystones.get(_i)).getTag("waystone_data");
                        assert dstTag != null;
                        Waystone destination = new Waystone(
                                dstTag.getInt("x"),
                                dstTag.getInt("y"),
                                dstTag.getInt("z"),
                                dstTag.getString("name"),
                                Bukkit.getWorld(Converter.uuidFromBytes(dstTag.getByteArray("world")))
                        );
                        playerSourceDestinationPairs.put(
                                (Player) event.getWhoClicked(),
                                new SourceDestinationPair(thisWaystone, destination)
                        );
                    }
                }
            });
        }
    }

    private static List<ItemStack> getKnownWaystones(Player player, Waystone ...toExclude) {
        Map<Integer, byte[]> values = new HashMap<>();
        values.put(1, Converter.uuidToBytes(player.getUniqueId()));
        int offset = playerPages.get(player) * 9;
        return DatabaseManager.makeExecuteQuery(
                "select w.name, w.x, w.y, w.z, w.world from `waystones` as w " +
                        "inner join `player's waystones` as pw " +
                        "on w.id = pw.waystone_id and pw.player = ? limit " + offset + ", 9",
                values,
                waystonesRS -> {
                    try {
                        List<Waystone> waystones = new ArrayList<>();
                        while (waystonesRS.next()) {
                            Waystone waystone = new Waystone(
                                    waystonesRS.getInt(2),
                                    waystonesRS.getInt(3),
                                    waystonesRS.getInt(4),
                                    waystonesRS.getString(1),
                                    Bukkit.getWorld(Converter.uuidFromBytes(waystonesRS.getBytes(5)))
                            );
                            if (!Arrays.stream(toExclude).collect(Collectors.toList()).contains(waystone))
                                waystones.add(waystone);
                        }
                        return waystones.stream()
                                .map(w -> {
                                    NBTTagCompound waystoneData = new NBTTagCompound();
                                    waystoneData.setInt("x", w.getX());
                                    waystoneData.setInt("y", w.getY());
                                    waystoneData.setInt("z", w.getZ());
                                    waystoneData.setString("name", w.getName());
                                    waystoneData.setByteArray("world", Converter.uuidToBytes(Objects.requireNonNull(w.getWorld()).getUID()));
                                    return new ItemBuilder(Material.PAPER)
                                            .setName(w.getName())
                                            .setLore(
                                                    "",
                                                    ChatColor.GOLD + "X: " + ChatColor.YELLOW + w.getX(),
                                                    ChatColor.GOLD + "Y: " + ChatColor.YELLOW + w.getY(),
                                                    ChatColor.GOLD + "Z: " + ChatColor.YELLOW + w.getZ(),
                                                    ChatColor.GOLD + "Мир: " + ChatColor.YELLOW + MiscUtils.getWorldName(w.getWorld())
                                            )
                                            .addNBT("waystone_data", waystoneData)
                                            .build();
                                })
                                .collect(Collectors.toList());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return Collections.emptyList();
                }
        );
    }

    private static void throwCantReachWaystoneException(@NotNull Player player, int x, int y, int z, @NotNull UUID world) {
        if (!player.getWorld().getUID().equals(world))
            throw new CantReachWaystoneException(String.format(
                    "Player %s somehow tried to open waystone at (%d, %d, %d, %s) though he was in the world %s",
                    player.getName(), x, y, z, Objects.requireNonNull(Bukkit.getWorld(world)).getName(), player.getWorld().getName()));
        else {
            throw new CantReachWaystoneException(String.format(
                    "Player %s somehow tried to open waystone at (%d, %d, %d, %s) though he was too far (%.2f, %.2f, %.2f)",
                    player.getName(), x, y, z, Objects.requireNonNull(Bukkit.getWorld(world)).getName(),
                    player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
        }
    }
}