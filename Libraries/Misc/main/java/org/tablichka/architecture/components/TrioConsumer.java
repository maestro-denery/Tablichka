package org.tablichka.architecture.components;

import java.util.Objects;

@FunctionalInterface
public interface TrioConsumer<A, B, C> {
    void accept(A a, B b, C c);

    default TrioConsumer<A, B, C> andThen(TrioConsumer<? super A, ? super B, ? super C> after) {
        Objects.requireNonNull(after);

        return (x, y, z) -> {
            accept(x, y, z);
            after.accept(x, y, z);
        };
    }
}
