package com.danikvitek.waystone.utils;

import com.danikvitek.waystone.Main;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DatabaseManager {
    private static boolean createDatabase = false;
    private static String db_host;
    private static int db_port;
    private static String db_name;
    private static String db_user;
    private static String db_password;

    private static DataSource dataSource;

    public static void setDb_host(String db_host) {
        DatabaseManager.db_host = db_host;
    }

    public static void setDb_port(int db_port) {
        DatabaseManager.db_port = db_port;
    }

    public static void setDb_name(String db_name) {
        DatabaseManager.db_name = db_name;
    }

    public static void setDb_user(String db_user) {
        DatabaseManager.db_user = db_user;
    }

    public static void setDb_password(String db_password) {
        DatabaseManager.db_password = db_password;
    }

    static MysqlConnectionPoolDataSource mcpDataSource;

    public static void connectToDB() {
        mcpDataSource = new MysqlConnectionPoolDataSource();
        mcpDataSource.setServerName(db_host);
        mcpDataSource.setPort(db_port);
        if (!createDatabase)
            mcpDataSource.setDatabaseName(db_name);
        mcpDataSource.setUser(db_user);
        mcpDataSource.setPassword(db_password);
        dataSource = mcpDataSource;
        if (!isValidConnection()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Не удалось подключиться к базе данных");
            Bukkit.getPluginManager().disablePlugin(Main.getPlugin(Main.class));
        }
    }

    private static boolean isValidConnection() {
        try {
            Connection connection = getConnection();
            if (connection != null)
                return connection.isValid(1000);
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static @Nullable Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void makeExecute(String query, @Nullable Map<Integer, Class<?>> values) {
        Connection connection = getConnection();
        if (connection != null)
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                if (values != null)
                    for (Map.Entry<Integer, Class<?>> value: values.entrySet())
                        ps.setObject(value.getKey(), value.getValue());
                ps.execute();
                connection.close();
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "QUERY: " + query);
                e.printStackTrace();
            }
    }

    public static boolean makeExecuteUpdate(String query, @Nullable Map<Integer, ?> values) {
        Connection connection = getConnection();
        if (connection != null)
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                if (values != null)
                    for (Map.Entry<Integer, ?> value: values.entrySet())
                        ps.setObject(value.getKey(), value.getValue());
                ps.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "QUERY: " + query);
                e.printStackTrace();
                return false;
            }
        return true;
    }

    public static @Nullable <T> T makeExecuteQuery(String query, @Nullable Map<Integer, ?> values, @Nullable Function<ResultSet, T> function) {
        Connection conn = getConnection();
        T result = null;
        if (conn != null) {
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                if (values != null)
                    for (Map.Entry<Integer, ?> value: values.entrySet())
                        ps.setObject(value.getKey(), value.getValue());
                ResultSet rs = ps.executeQuery();
                if (function != null)
                    result = function.apply(rs);
                conn.close();
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "QUERY: " + query);
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void createTables() throws IOException {
        if (createDatabase) {
            makeExecuteUpdate(
                    "create schema if not exists `" + db_name + "`;",
                    null
            );
            mcpDataSource.setDatabaseName(db_name);
            dataSource = mcpDataSource;
        }
        makeExecuteUpdate(
                String.format("""
                        create table if not exists `%s`.`waystones`(
                            id     bigint      not null auto_increment,
                            x      integer     not null,
                            y      integer     not null,
                            z      integer     not null,
                            world  binary(16)  not null,
                            name   varchar(50) not null,
                            constraint waystone_pk primary key (id),
                            constraint waystone_uq1 unique (x, y, z, world),
                            constraint waystone_uq2 unique (y, z, world, x),
                            constraint waystone_uq3 unique (z, world, x, y),
                            constraint waystone_uq4 unique (world, x, y, z)
                        );
                        """, db_name),
                null
        );
        makeExecuteUpdate(
                String.format("""
                        create table if not exists `%s`.`player's waystones`(
                            player      binary(16) not null,
                            waystone_id bigint     not null,
                            constraint player_waystone_pk primary key (player, waystone_id),
                            constraint waystone_fk foreign key (waystone_id) references `waystones` (id) on delete cascade on update cascade
                        );
                        """, db_name),
                null
        );
    }

    public static void setCreateDatabase(boolean createDatabase) {
        DatabaseManager.createDatabase = createDatabase;
    }
}
