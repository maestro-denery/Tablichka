package io.denery;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.minecraft.world.entity.Entity;

/**
 * Class where you register custom Entities with Model in ModelEngine.
 * As you can see it uses mixed Singleton and Builder pattern.
 */
public class EntityRegistry {
    private EntityRegistry() {}

    private static EntityRegistry instance = null;

    //public static EntityBuilder newRegistry() {

    //}

    public static EntityRegistry getInstance() {
        if (instance == null) throw new NullPointerException("You need to register Entities!");
        return instance;
    }

    ReferenceArraySet<Entity> entities = new ReferenceArraySet<>();

    private class EntityBuilder {
        private EntityBuilder() {}

        public EntityBuilder registerEntity() {
            // TODO registering entity with attached model.
            return this;
        }

        public EntityRegistry build() {
            return EntityRegistry.this;
        }
    }
}
