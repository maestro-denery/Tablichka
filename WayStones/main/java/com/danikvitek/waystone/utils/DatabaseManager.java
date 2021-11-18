package com.danikvitek.waystone.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.Map;
import java.util.function.Function;

public class DatabaseManager {
    private final String dbURL, dbUser, dbPassword;
    private final boolean createDatabase;
    private final String dbName;

    public DatabaseManager(String dbHost, int dbPort, String dbName, String dbUser, String dbPassword, boolean createDatabase) {
        this.dbName = dbName;
        this.createDatabase = createDatabase;
        this.dbURL = String.format("jdbc:mysql://%s:%d/%s", dbHost, dbPort, createDatabase ? "" : dbName);
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        createTables();
    }

    private void createTables() {
        try (
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                Statement stmt = conn.createStatement()
        ) {
            if (createDatabase) {
                stmt.executeUpdate("create table if not exists `" + dbName + "`;");
            }
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

    public void makeExecute(String query, @Nullable Map<Integer, Object> values) {
        try (
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                PreparedStatement ps = conn.prepareStatement(query);
        ) {
            if (values != null)
                for (Map.Entry<Integer, Object> value : values.entrySet())
                    ps.setObject(value.getKey(), value.getValue());
            ps.execute();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "QUERY: " + query);
            e.printStackTrace();
        }

    }

    public boolean makeExecuteUpdate(String query, @Nullable Map<Integer, Object> values) {
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
    }

    public @Nullable <T> T makeExecuteQuery(String query, @Nullable Map<Integer, Object> values, @Nullable Function<ResultSet, T> function) {
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
    }
}
