import io.denery.EntityRegistry;
import io.denery.entity.CustomizableEntityType;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class EntityRegistryTest {

    @Test
    void singletonTest() {
        CustomizableEntityType entity = new CustomizableEntityType("SampleEntity");
        EntityRegistry.newRegistry()
                .register(entity)
                .apply();

        assertEquals(1, EntityRegistry.getInstance().getRegisteredEntities().size());
    }

    @Test
    void hashSetTest() {
        CustomizableEntityType entity = new CustomizableEntityType("SampleEntity");
        EntityRegistry registry = EntityRegistry.newRegistry()
                .register(entity)
                .register(entity)
                .apply();

        assertEquals(1, registry.getRegisteredEntities().size());
    }
}
