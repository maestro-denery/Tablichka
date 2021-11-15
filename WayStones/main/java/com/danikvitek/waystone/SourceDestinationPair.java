package com.danikvitek.waystone;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SourceDestinationPair {
    @NotNull
    private Waystone source;
    @NotNull
    private Waystone destination;

    public SourceDestinationPair(@NotNull Waystone source, @NotNull Waystone destination) {
        this.source = source;
        this.destination = destination;
    }

    public @NotNull Waystone getSource() {
        return source;
    }

    public @NotNull Waystone getDestination() {
        return destination;
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
        if (!(o instanceof SourceDestinationPair that)) return false;
        return getSource().equals(that.getSource()) && getDestination().equals(that.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination());
    }
}
