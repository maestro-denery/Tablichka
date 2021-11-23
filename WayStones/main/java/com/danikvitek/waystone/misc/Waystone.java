package com.danikvitek.waystone.misc;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Waystone {
    private final int x, y, z;
    @NotNull
    private final String name;
    @Nullable
    private final World world;

    public Waystone(int x, int y, int z, @NotNull String name, @Nullable World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public @NotNull String getName() {
        return name;
    }

    public @Nullable World getWorld() {
        return world;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Waystone waystone)) return false;
        return getX() == waystone.getX() && getY() == waystone.getY() && getZ() == waystone.getZ() && Objects.equals(getWorld(), waystone.getWorld());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ(), getWorld());
    }
}
