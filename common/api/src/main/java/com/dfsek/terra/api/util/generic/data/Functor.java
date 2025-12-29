package com.dfsek.terra.api.util.generic.data;

import com.dfsek.terra.api.util.generic.kinds.K;

import java.util.function.Function;


public interface Functor<T, F extends Functor<?, F>> extends K<F, T> {
    <U> Functor<U, F> map(Function<T, U> map);
}
