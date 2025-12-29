package com.dfsek.terra.api.util.generic;

/**
 * Kind
 */
public interface K<T, U> {
    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }
}
