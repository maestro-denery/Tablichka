package com.danikvitek.waystone;

import com.danikvitek.waystone.utils.*;
import com.danikvitek.waystone.utils.gui.Menu;
import com.danikvitek.waystone.utils.gui.MenuHandler;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomStack;
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
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tablichka.utils.Converter;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WaystoneManager implements Listener {
    WayStonesPlugin plugin;

    WaystoneManager(WayStonesPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull public static final String TOP_HALF_ID = "ingredients:platinum_block";
    @NotNull public static final String BOTTOM_HALF_ID = "ingredients:cobalt_block";
    @NotNull public static final String WAYSTONE_GIU_ID = "hub_furniture:jukebox_main";
    @NotNull public static final String GUI_ICON_BACK_ID = "mcguis:icon_back";
    @NotNull public static final String GUI_ICON_NEXT_ID = "mcguis:icon_next";
    @NotNull public static final String GUI_ICON_APPLY_ID = "mcguis:icon_apply";
    @NotNull public static final String GUI_ICON_CANCEL_ID = "mcguis:icon_cancel";
    public static final int BUTTON_SLOT_BACK = 9;
    public static final int BUTTON_SLOT_NEXT = 17;
    public static final int BUTTON_SLOT_APPLY_CANCEL = 13;

    private static final Map<Player, Integer> playerPages = new HashMap<>();

    @EventHandler
    public void onWaystoneInteract(CustomBlockInteractEvent event) {
        if (!event.getPlayer().isSneaking()) {
            if (Objects.equals(event.getNamespacedID(), TOP_HALF_ID)) {
                CustomBlock bottomHalf = CustomBlock.byAlreadyPlaced(event.getBlockClicked().getRelative(BlockFace.DOWN));
                if (bottomHalf != null && bottomHalf.getNamespacedID().equals(BOTTOM_HALF_ID)) {
                    int x = event.getBlockClicked().getX(),
                            y = event.getBlockClicked().getY() - 1,
                            z = event.getBlockClicked().getZ();
                    UUID world = event.getBlockClicked().getWorld().getUID();
                    interactWithWaystone(event.getPlayer(), x, y, z, world);
                }
            } else if (Objects.equals(event.getNamespacedID(), BOTTOM_HALF_ID)) {
                CustomBlock topHalf = CustomBlock.byAlreadyPlaced(event.getBlockClicked().getRelative(BlockFace.UP));
                if (topHalf != null && topHalf.getNamespacedID().equals(TOP_HALF_ID)) {
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
            if (Objects.equals(customBlock.getNamespacedID(), TOP_HALF_ID)) {
                CustomBlock bottomHalf = CustomBlock.byAlreadyPlaced(event.getBlock().getRelative(BlockFace.DOWN));
                if (bottomHalf != null && bottomHalf.getNamespacedID().equals(BOTTOM_HALF_ID)) {
                    int x = event.getBlock().getX(),
                            y = event.getBlock().getY() - 1,
                            z = event.getBlock().getZ();
                    UUID world = event.getBlock().getWorld().getUID();
                    breakWaystone(x, y, z, world);
                }
            } else if (Objects.equals(customBlock.getNamespacedID(), BOTTOM_HALF_ID)) {
                CustomBlock topHalf = CustomBlock.byAlreadyPlaced(event.getBlock().getRelative(BlockFace.UP));
                if (topHalf != null && topHalf.getNamespacedID().equals(TOP_HALF_ID)) {
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
            String waystoneName = plugin.getDatabaseManager().makeExecuteQuery(
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
            List<Player> players = plugin.getDatabaseManager().makeExecuteQuery(
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
            plugin.getDatabaseManager().makeExecuteUpdate(
                    "delete from `waystones` where id = '" + waystoneId + "';",
                    null);
            if (players != null && waystoneName != null)
                for (Player p : players) {
                    p.sendMessage(String.format(ChatColor.RED + "Обелиск \"%s\" разрушен", waystoneName));
                    SourceDestinationPair.stopAndClearByPlayer(p);
                }
        }
    }

    private void interactWithWaystone(@NotNull Player player, int x, int y, int z, @NotNull UUID world) {
        Integer existingId = getWaystoneID(x, y, z, world);
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, Converter.uuidToBytes(player.getUniqueId()));
        if (existingId != null) {
            Optional<Boolean> knownWaystones = Optional.ofNullable(plugin.getDatabaseManager().makeExecuteQuery(
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

    private void registerWaystoneForPlayer(@NotNull Player player, int x, int y, int z, @NotNull UUID world, int existingId) {
        if (player.getWorld().getUID().equals(world) && player.getLocation().toVector().distance(new Vector(x, y, z)) <= 6) {
            HashMap<Integer, Object> values = new HashMap<>();
            values.put(1, Converter.uuidToBytes(player.getUniqueId()));
            plugin.getDatabaseManager().makeExecuteUpdate(
                    "insert into `player's waystones` values (?, '" + existingId + "');",
                    values);
            player.sendMessage(ChatColor.GOLD + "Обелиск сохранён");
            WayStonesPlugin.log(player.getName() + " discovered waystone " + existingId);
        }
        else throwCantReachWaystoneException(player, x, y, z, world);
    }

    private void registerWaystone(@NotNull Player player, int x, int y, int z, @NotNull UUID world) {
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
                        HashMap<Integer, Object> values = new HashMap<>();
                        values.put(1, Converter.uuidToBytes(world));
                        plugin.getDatabaseManager().makeExecuteUpdate(
                                "insert into `waystones` (x, y, z, world, name) values (" +
                                        "'" + x + "', '" + y + "', '" + z + "', ?, '" + t + "');",
                                values
                        );
                        p.sendMessage(ChatColor.GOLD + "Обелиск сформирован");
                        return AnvilGUI.Response.close();
                    })
                    .plugin(WayStonesPlugin.getPlugin(WayStonesPlugin.class))
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

    private @Nullable Integer getWaystoneID(int x, int y, int z, @NotNull UUID world) {
        HashMap<Integer, Object> values = new HashMap<>();
        values.put(1, Converter.uuidToBytes(world));
        return plugin.getDatabaseManager().makeExecuteQuery(
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

    private @Nullable String getWaystoneName(int waystoneId) {
        return plugin.getDatabaseManager().makeExecuteQuery(
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

    private void openWaystoneGUI(@NotNull Player player, int waystoneId, int x, int y, int z, @NotNull UUID world)
            throws IllegalStateException {
        if (player.getWorld().getUID().equals(world) && player.getLocation().toVector().distance(new Vector(x, y, z)) <= 6) {
            String waystoneName = getWaystoneName(waystoneId);
            if (waystoneName != null) {
                TexturedInventoryWrapper gui = new TexturedInventoryWrapper(
                        null,
                        18,
                        "Обелиск " + waystoneName,
                        new FontImageWrapper(WAYSTONE_GIU_ID)
                );
                Menu waystoneMenu = new Menu(gui);
                playerPages.put(player, 0);
                redrawWaystones(player, waystoneMenu, new Waystone(x, y, z, waystoneName, Bukkit.getWorld(world)));
                MenuHandler.openMenu(player, waystoneMenu);
            }
        }
        else throwCantReachWaystoneException(player, x, y, z, world);
    }

    private void redrawWaystones(@NotNull Player player, Menu waystoneMenu, Waystone thisWaystone) {
        List<ItemStack> knownWaystones = getKnownWaystones(player, thisWaystone);
        setWaystonesIcons(player, waystoneMenu, knownWaystones, thisWaystone);
        setControlButtons(player, waystoneMenu, knownWaystones, thisWaystone);
    }

    private void setControlButtons(@NotNull Player player, Menu waystoneMenu, List<ItemStack> knownWaystones, Waystone thisWaystone) {
        waystoneMenu.setButton(BUTTON_SLOT_BACK, new Button(CustomStack.getInstance(GUI_ICON_BACK_ID).getItemStack()) { // todo: put left arrow button
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
                    setWaystonesIcons(player, menu, newKnownWaystones, thisWaystone);
                    selected.remove(player);
                    MenuHandler.reloadMenu(player);
                }
            }
        });
        waystoneMenu.setButton(BUTTON_SLOT_NEXT, new Button(CustomStack.getInstance(GUI_ICON_NEXT_ID).getItemStack()) { // todo: put right arrow button
            @Override
            public void onClick(Menu menu, InventoryClickEvent event) {
                event.setCancelled(true);
                playerPages.put(player, playerPages.get(player) + 1);
                List<ItemStack> newKnownWaystones = getKnownWaystones(player, thisWaystone);
                if (newKnownWaystones.size() == 0) {
                    playerPages.put(player, playerPages.get(player) - 1);
                    newKnownWaystones = getKnownWaystones(player, thisWaystone);
                }
                setWaystonesIcons(player, menu, newKnownWaystones, thisWaystone);
                selected.remove(player);
                MenuHandler.reloadMenu(player);
            }
        });
    }

    private static final Map<Player, Integer> selected = new HashMap<>();

    private static void setWaystonesIcons(Player player, Menu waystoneMenu, List<ItemStack> knownWaystones, Waystone thisWaystone) {
        for (int i = 0; i < 9; i++) {
            int _i = i;
            waystoneMenu.setButton(i, new Button(_i < knownWaystones.size() ? knownWaystones.get(_i) : null) {
                @Override
                public void onClick(Menu menu, InventoryClickEvent event) {
                    event.setCancelled(true);
                    if (_i < knownWaystones.size()) {
                        selected.put((Player) event.getWhoClicked(), _i);
                        Bukkit.getPluginManager().registerEvents(
                                new Listener() {
                                    @EventHandler
                                    public void onInventoryClose(InventoryCloseEvent e) {
                                        selected.remove(player);
                                        HandlerList.unregisterAll(this);
                                    }
                                },
                                WayStonesPlugin.getPlugin(WayStonesPlugin.class)
                        );
                        drawApplyCancelButton(player, waystoneMenu, knownWaystones, thisWaystone);
                        MenuHandler.reloadMenu(player);
                    }
                }
            });
        }
        drawApplyCancelButton(player, waystoneMenu, knownWaystones, thisWaystone);
    }

    @SuppressWarnings("deprecation")
    private static void drawApplyCancelButton(Player player, Menu waystoneMenu, List<ItemStack> knownWaystones, Waystone thisWaystone) {
        waystoneMenu.setButton(BUTTON_SLOT_APPLY_CANCEL, new Button(
                selected.containsKey(player)
                ? ((Supplier<ItemStack>) () -> {
                    ItemMeta selectedMeta = knownWaystones.get(selected.get(player)).getItemMeta();
                    List<String> lore = new ArrayList<>(Objects.requireNonNull(selectedMeta.getLore()));
                    lore.add(1, ChatColor.GOLD + "Пункт назначения: " + ChatColor.YELLOW + ChatColor.stripColor(selectedMeta.getDisplayName()));
                    return new ItemBuilder(CustomStack.getInstance(GUI_ICON_APPLY_ID).getItemStack())
                            .setName(ChatColor.GREEN + "Подтвердить")
                            .setLore(lore)
                            .build();
                }).get()
                : new ItemBuilder(CustomStack.getInstance(GUI_ICON_CANCEL_ID).getItemStack())
                        .setName(ChatColor.RED + "Отменить")
                        .build()
        ) {
            @Override
            public void onClick(Menu menu, InventoryClickEvent event) {
                event.setCancelled(true);
                if (selected.containsKey(player)) {
                    if (!SourceDestinationPair.hasSelection(player)) {
                        NBTTagCompound dstTag = (NBTTagCompound) new NBTManager(knownWaystones.get(selected.get(player)))
                                .getTag("waystone_data");
                        assert dstTag != null;
                        Waystone destination = new Waystone(
                                dstTag.getInt("x"),
                                dstTag.getInt("y"),
                                dstTag.getInt("z"),
                                dstTag.getString("name"),
                                Bukkit.getWorld(Converter.uuidFromBytes(dstTag.getByteArray("world")))
                        );
                        SourceDestinationPair.registerNewPair(
                                player,
                                thisWaystone,
                                destination
                        );
                    }
                }
                MenuHandler.closeMenu(player);
            }
        });
    }

    private List<ItemStack> getKnownWaystones(Player player, Waystone toExclude) {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, Converter.uuidToBytes(player.getUniqueId()));
        int offset = playerPages.get(player) * 9;
        return plugin.getDatabaseManager().makeExecuteQuery(
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
                            if (!toExclude.equals(waystone))
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
                                    return new NBTManager(
                                            new ItemBuilder(Material.PAPER)
                                                    .setName(w.getName())
                                                    .setLore(
                                                            "",
                                                            ChatColor.GOLD + "X: " + ChatColor.YELLOW + w.getX(),
                                                            ChatColor.GOLD + "Y: " + ChatColor.YELLOW + w.getY(),
                                                            ChatColor.GOLD + "Z: " + ChatColor.YELLOW + w.getZ(),
                                                            ChatColor.GOLD + "Мир: " + ChatColor.YELLOW + MiscUtils.getWorldName(w.getWorld())
                                                    )
                                                    .build()
                                    ).addTag("waystone_data", waystoneData).build();
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