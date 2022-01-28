package io.denery.entityregistry;

import io.denery.entityregistry.entity.CustomizableEntityType;
import org.foton.architecture.pattern.Applier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class where you register custom Entity Type with Model in ModelEngine.
 * As you can see it uses mixed Singleton and Builder pattern, I called this pattern "Registry",
 * and modified builder - "Applier".
 */
public final class EntityTypeRegistry {
    private static final Logger logger = LoggerFactory.getLogger("EntityRegistryLib");
    private EntityTypeRegistry() {}

    private static EntityTypeRegistry instance = null;

    public static EntityApplier newRegistry() {
        return getInstance().new EntityApplier();
    }

    public static EntityTypeRegistry getInstance() {
        if (instance == null) {
            instance = new EntityTypeRegistry();
        }
        return instance;
    }

    private Map<String, CustomizableEntityType> types = new HashMap<>();
    private final Map<String, CustomizableEntityType> localReg = new HashMap<>();

    public Map<String, CustomizableEntityType> getRegisteredEntities() {
        return types;
    }

    public List<CustomizableEntityType> getRegisteredEntitiesList() {
        return new ArrayList<>(types.values());
    }

    public class EntityApplier implements Applier<EntityTypeRegistry> {
        private EntityApplier() {}

        public EntityApplier register(CustomizableEntityType type) {
            Option<String> optID = type.getID();
            if (optID.isDefined()) {
                localReg.put(optID.get(), type);
                return this;
            }
            logger.error("There is no ID for Entity type, please set it!");
            return this;
        }

        @Override
        public EntityTypeRegistry apply() {
            types = localReg;
            return EntityTypeRegistry.this;
        }
    }
}
