package io.denery;

import io.denery.entity.CustomizableEntityType;
import org.tablichka.architecture.Applier;

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
    private final Map<String, CustomizableEntityType> localReg = new HashMap<>();

    public Map<String, CustomizableEntityType> getRegisteredEntities() {
        return types;
    }

    public class EntityApplier implements Applier<EntityRegistry> {
        private EntityApplier() {}

        public EntityApplier register(CustomizableEntityType type) {
            localReg.put(type.name(), type);
            return this;
        }

        @Override
        public EntityRegistry apply() {
            types = localReg;
            return EntityRegistry.this;
        }
    }
}
