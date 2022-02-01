package com.danikvitek.waystones.misc.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public final class DatabaseManager {
    private String dbURL, dbUser, dbPassword;
    private boolean createDatabase;
    private String dbName;

    private DatabaseManager() {} // Singleton

    private static DatabaseManager instance;
    private static boolean isInitialized = false;

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    public void init(String dbHost, int dbPort, String dbName, String dbUser, String dbPassword, boolean createDatabase)
    throws IllegalStateException {
        if (!isInitialized) {
            this.dbName = dbName;
            this.createDatabase = createDatabase;
            this.dbURL = String.format("jdbc:mysql://%s:%d/%s", dbHost, dbPort, createDatabase ? "" : dbName);
            this.dbUser = dbUser;
            this.dbPassword = dbPassword;
            isInitialized = true;
            createTables();
        }
        else throw new IllegalStateException("DatabaseManager is already initialized");
    }

    private void createTables() {
        try (
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                Statement stmt = conn.createStatement()
        ) {
            if (createDatabase) stmt.executeUpdate("create table if not exists `" + dbName + "`;");
            String sql =
                    "create table if not exists " + (createDatabase ? "`" + dbName + "`." : "") + "`waystones`(" +
                    "    id     bigint      not null auto_increment," +
                    "    x      integer     not null," +
                    "    y      integer     not null," +
                    "    z      integer     not null," +
                    "    world  binary(16)  not null," +
                    "    name   varchar(50) not null," +
                    "    constraint waystone_pk primary key (id)," +
                    "    constraint waystone_uq1 unique (x, y, z, world)," +
                    "    constraint waystone_uq2 unique (y, z, world, x)," +
                    "    constraint waystone_uq3 unique (z, world, x, y)," +
                    "    constraint waystone_uq4 unique (world, x, y, z)" +
                    ");";
            stmt.executeUpdate(sql);
            sql =   "create table if not exists " + (createDatabase ? "`" + dbName + "`." : "") + "`player's waystones`(" +
                    "    player      binary(16) not null," +
                    "    waystone_id bigint     not null," +
                    "    constraint player_waystone_pk primary key (player, waystone_id)," +
                    "    constraint waystone_fk foreign key (waystone_id) references " + (createDatabase ? "`" + dbName + "`." : "") + "`waystones` (id) on delete cascade on update cascade" +
                    ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> makeExecuteUpdate(String query, @Nullable Map<Integer, Object> values) 
    throws IllegalStateException {
        if (isInitialized)
            return CompletableFuture.supplyAsync(() -> {
                try (
                        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                        PreparedStatement ps = conn.prepareStatement(query);
                ) {
                    if (values != null)
                        for (Map.Entry<Integer, Object> value : values.entrySet())
                            ps.setObject(value.getKey(), value.getValue());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "QUERY: " + query);
                    e.printStackTrace();
                    return false;
                }
                return true;
            });
        else throw new IllegalStateException("DatabaseManager is not initialized");
    }

    public <T> CompletableFuture<T> makeExecuteQuery(String query, @Nullable Map<Integer, Object> values, @Nullable Function<ResultSet, T> function) 
    throws IllegalStateException {
        if (isInitialized)
            return CompletableFuture.supplyAsync(() -> {
                T result = null;
                try (
                        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                        PreparedStatement ps = conn.prepareStatement(query);
                ) {
                    if (values != null)
                        for (Map.Entry<Integer, Object> value : values.entrySet())
                            ps.setObject(value.getKey(), value.getValue());
                    ResultSet rs = ps.executeQuery();
                    if (function != null)
                        result = function.apply(rs);
                } catch (SQLException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "QUERY: " + query);
                    e.printStackTrace();
                }
                return result;
            });
        else throw new IllegalStateException("DatabaseManager is not initialized");
    }
}
