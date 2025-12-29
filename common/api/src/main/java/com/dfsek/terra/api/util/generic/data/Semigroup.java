package com.dfsek.terra.api.util.generic.data;

import com.dfsek.terra.api.util.generic.kinds.K;


public interface Semigroup<T, S extends Semigroup<?, S>> extends K<S, T> {
    Semigroup<T, S> multiply(S t);
}
