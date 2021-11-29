package io.denery.entityregistry.entity;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.denery.entityregistry.EntityTypeRegistry;
import io.denery.entityregistry.behaviour.BehaviourCategories;
import org.bukkit.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Represents the "actual" Customized Entity on a server.
 * @param <T> Type of living entity which will be "customized" with model and pathfinder.
 */
public class CustomizableEntity<T extends LivingEntity> {
    private static final Logger logger = LoggerFactory.getLogger("EntityRegistryLib");

    protected AbstractCustomizableEntityType type;
    protected T origin;
    private ModeledEntity modeledEntity;
    //protected BehaviourCategories.Behaviour behaviour;

    /*public CustomizableEntity(AbstractCustomizableEntityType type, T entity, BehaviourCategories behaviour) {
        this.type = type;
        this.origin = entity;
        //this.behaviour = behaviour;
    }

     */

    public CustomizableEntity(AbstractCustomizableEntityType type, T entity) {
        this.type = type;
        this.origin = entity;
    }

    /*public CustomizableEntity(String id, T entity, BehaviourCategories behaviour) {
        this.type = EntityTypeRegistry.getInstance().getRegisteredEntities().get(id);
        this.origin = entity;
        //this.behaviour = behaviour;
    }

     */

    public CustomizableEntity(String id, T entity) {
        this.type = EntityTypeRegistry.getInstance().getRegisteredEntities().get(id);
        this.origin = entity;
    }

    // Created For CustomizableEntityBuilder.
    protected CustomizableEntity() {}

    public void modelEntity() {
        ActiveModel activeModel = ModelEngineAPI.api.getModelManager().createActiveModel(type.id);
        Optional<ModeledEntity> me = Optional.ofNullable(ModelEngineAPI.api.getModelManager().createModeledEntity(origin));
        if (me.isEmpty()) throw new RuntimeException("Couldn't create modeled entity: " + origin.getName());
        modeledEntity = me.get();

        modeledEntity.addActiveModel(activeModel);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);
    }

    public AbstractCustomizableEntityType getType() {
        return type;
    }

    public Optional<T> getOriginEntity() {
        return Optional.ofNullable(origin);
    }

    public Optional<ModeledEntity> getModeledEntity() {
        return Optional.ofNullable(modeledEntity);
    }

    //public Optional<BehaviourCategories.Behaviour> getBehaviour() {
    //    return Optional.ofNullable(behaviour);
    //}

    //public void setBehaviour(BehaviourCategories.Behaviour behaviour) {
    //    this.behaviour = behaviour;
    //}
}