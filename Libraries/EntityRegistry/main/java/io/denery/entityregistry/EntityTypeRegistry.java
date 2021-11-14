package io.denery.entityregistry;

import io.denery.entityregistry.entity.AbstractCustomizableEntityType;
import io.denery.entityregistry.entity.CustomizableEntityTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tablichka.architecture.pattern.Applier;

import java.util.*;

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

    private Map<String, AbstractCustomizableEntityType> types = new HashMap<>();
    private final Map<String, AbstractCustomizableEntityType> localReg = new HashMap<>();

    public Map<String, AbstractCustomizableEntityType> getRegisteredEntities() {
        return types;
    }

    public List<AbstractCustomizableEntityType> getRegisteredEntitiesList() {
        return new ArrayList<>(types.values());
    }

    public class EntityApplier implements Applier<EntityTypeRegistry> {
        private EntityApplier() {}

        public EntityApplier register(AbstractCustomizableEntityType type) {
            Optional<String> optID = type.getID();
            if (optID.isPresent()) {
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
