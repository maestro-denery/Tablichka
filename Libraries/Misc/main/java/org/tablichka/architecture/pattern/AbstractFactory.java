package org.tablichka.architecture.pattern;

import java.util.Optional;

public interface AbstractFactory<T, R> {
    Optional<T> create(R animalType);
}
