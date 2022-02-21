package org.foton.architecture.pattern;

import java.util.Optional;

@Deprecated(forRemoval = true)
public interface Registrable<ID> {
    Optional<ID> getID();
}
