package io.denery;

import io.denery.entity.CustomizableEntityType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class where you register custom Entities with Model in ModelEngine.
 * As you can see it uses mixed Singleton and Builder pattern.
 */
public final class EntityRegistry {
    private EntityRegistry() {}

    private static EntityRegistry instance = null;

    public static EntityBuilder newRegistry() {
        return getInstance().new EntityBuilder();
    }

    public static EntityRegistry getInstance() {
        if (instance == null) {
            instance = new EntityRegistry();
        }
        return instance;
    }

    private Map<String, CustomizableEntityType> types = new HashMap<>();
    private Map<String, CustomizableEntityType> localreg = new HashMap<>();

    public Map<String, CustomizableEntityType> getRegisteredEntities() {
        return types;
    }

    public class EntityBuilder {
        private EntityBuilder() {}

        public EntityBuilder register(CustomizableEntityType type) {
            localreg.put(type.getName(), type);
            return this;
        }

        public EntityRegistry build() {
            types = localreg;
            return EntityRegistry.this;
        }
    }
}
