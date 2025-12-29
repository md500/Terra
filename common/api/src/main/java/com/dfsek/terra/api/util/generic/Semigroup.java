package com.dfsek.terra.api.util.generic;

public interface Semigroup<T, S extends K<S, T>> {
    Semigroup<T, S> multiply(S t);
}
