package org.tablichka.architecture.pattern;

import java.util.Optional;

public interface Registrable<ID> {
    Optional<ID> getID();
}
