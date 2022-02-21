package org.foton.architecture.components;

import java.util.Objects;

@Deprecated
public class TrioTuple<A, B, C> {
    private A first;
    private B second;
    private C third;

    public TrioTuple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird() {
        return third;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    public void setThird(C third) {
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrioTuple<?, ?, ?> trioTuple)) return false;
        return Objects.equals(getFirst(), trioTuple.getFirst()) && Objects.equals(getSecond(), trioTuple.getSecond()) && Objects.equals(getThird(), trioTuple.getThird());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond(), getThird());
    }
}
