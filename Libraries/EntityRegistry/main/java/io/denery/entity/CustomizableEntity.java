package io.denery.entity;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import io.denery.EntityRegistry;
import io.denery.behaviour.Behaviour;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import java.util.Optional;

public abstract class CustomizableEntity {
    private final LivingEntity origin;
    private final Mob originMob;
    private final ActiveModel model;
    private final ModeledEntity modeledEntity;
    private final CustomizableEntityType type;
    private Behaviour behaviour;
    public CustomizableEntity(String registeredName, LivingEntity origin, Behaviour behaviour) {
        this.origin = origin;
        this.originMob = (Mob) origin;
        this.type = EntityRegistry.getInstance().getRegisteredEntities().get(registeredName);
        this.behaviour = behaviour;

        Optional<ActiveModel> activeModel =  Optional.ofNullable(ModelEngineAPI.api.getModelManager().createActiveModel(registeredName));
        if (activeModel.isEmpty()) throw new RuntimeException("Couldn't load model name: " + registeredName);
        model = activeModel.get();

        Optional<ModeledEntity> me = Optional.ofNullable(ModelEngineAPI.api.getModelManager().createModeledEntity(origin));
        if (me.isEmpty()) throw new RuntimeException("Couldn't create modeled entity: " + origin);
        modeledEntity = me.get();

        modeledEntity.addActiveModel(model);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);

    }

    public LivingEntity getOrigin() {
        return origin;
    }

    public CustomizableEntityType getType() {
        return type;
    }

    public ActiveModel getModel() {
        return model;
    }

    public ModeledEntity getModeledEntity() {
        return modeledEntity;
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }
}
