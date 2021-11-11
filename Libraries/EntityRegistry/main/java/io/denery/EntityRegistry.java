package io.denery;

import io.denery.entity.CustomizableEntityType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class where you register custom Entities with Model in ModelEngine.
 * As you can see it uses mixed Singleton and Builder pattern.
 */
public final class EntityRegistry {
    private EntityRegistry() {}

    private static EntityRegistry instance = null;

    public static EntityApplier newRegistry() {
        return getInstance().new EntityApplier();
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

    public class EntityApplier {
        private EntityApplier() {}

        public EntityApplier register(CustomizableEntityType type) {
            localreg.put(type.name(), type);
            return this;
        }

        public EntityRegistry apply() {
            types = localreg;
            return EntityRegistry.this;
        }
    }
}
