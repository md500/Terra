package com.dfsek.terra.api.util.generic;

public interface Semigroup<T, S extends Semigroup<?, S>> {
    Semigroup<T, S> multiply(S t);
}
