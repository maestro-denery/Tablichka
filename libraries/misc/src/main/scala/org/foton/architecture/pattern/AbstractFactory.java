package org.foton.architecture.pattern;

import java.util.Optional;

@Deprecated(forRemoval = true)
public interface AbstractFactory<T, R> {
    Optional<T> create(R animalType);
}
