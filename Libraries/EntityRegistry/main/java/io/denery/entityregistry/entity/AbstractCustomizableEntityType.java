package io.denery.entityregistry.entity;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import org.bukkit.entity.EntityType;
import org.tablichka.architecture.pattern.Registrable;

import java.util.Optional;

public abstract class AbstractCustomizableEntityType implements Registrable<String> {
    /**
     * Type's ID, must fit blueprint's ID.
     */
    protected String id;
    protected EntityType originType;
    protected ActiveModel activeModel;

    public Optional<String> getID() {
        return Optional.ofNullable(id);
    }

    public Optional<EntityType> getOriginType() {
        return Optional.ofNullable(originType);
    }

    public Optional<ActiveModel> getActiveModel() {
        return Optional.ofNullable(activeModel);
    }

    public void setActiveModel(ActiveModel activeModel) {
        this.activeModel = activeModel;
    }
}
