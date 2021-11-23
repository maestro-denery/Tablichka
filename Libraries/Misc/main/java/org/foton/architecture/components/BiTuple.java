package org.foton.architecture.components;

import java.util.Objects;

public class BiTuple<A, B> {
    private A first;
    private B second;

    public BiTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiTuple<?, ?> biTuple)) return false;
        return Objects.equals(getFirst(), biTuple.getFirst()) && Objects.equals(getSecond(), biTuple.getSecond());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }
}
