package io.denery.entityregistry.entity;

import org.bukkit.entity.LivingEntity;

public final class CustomizableEntityBuilder<T extends LivingEntity> extends CustomizableEntity<T> {
    private CustomizableEntityBuilder() {}

    public static <TT extends LivingEntity> CustomizableEntityBuilder<TT> newBuilder() {
        return new CustomizableEntityBuilder<>();
    }

    public CustomizableEntityBuilder<T> applyOriginEntity(T originEntity) {
        super.origin = originEntity;
        return this;
    }

    public CustomizableEntityBuilder<T> applyType(AbstractCustomizableEntityType type) {
        super.type = type;
        return this;
    }

    /*
    public CustomizableEntityBuilder<T> applyBehaviour(AbstractBehaviour behaviour) {
        super.behaviour = behaviour;
        return this;
    }
    
     */

    //public CustomizableEntity<T> build() {
        //return new CustomizableEntity<T>(type, origin, behaviour);
    //}
}
