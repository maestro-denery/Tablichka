package io.denery.entityregistry.entity;

import org.bukkit.entity.EntityType;

/**
 * Class representing EntityType which you can customize (main feature: model from ModelEngine).
 */
public final class CustomizableEntityTypeBuilder extends AbstractCustomizableEntityType {
    private CustomizableEntityTypeBuilder() {}

    public static TypeBuilder newBuilder() {
        return new CustomizableEntityTypeBuilder().new TypeBuilder();
    }

    public class TypeBuilder {
        private TypeBuilder() {}

        public TypeBuilder setID(String ID) {
            CustomizableEntityTypeBuilder.this.id = ID;
            return this;
        }

        public TypeBuilder setOriginType(EntityType type) {
            if (!type.isAlive()) throw new IllegalStateException("Origin Entity must be alive!");
            CustomizableEntityTypeBuilder.this.originType = type;
            return this;
        }

        public CustomizableEntityTypeBuilder build() {
            return CustomizableEntityTypeBuilder.this;
        }
    }
}
