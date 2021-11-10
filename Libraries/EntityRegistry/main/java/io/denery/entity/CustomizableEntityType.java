package io.denery.entity;

public record CustomizableEntityType(String name) {
    public String getName() {
        return name;
    }
}
