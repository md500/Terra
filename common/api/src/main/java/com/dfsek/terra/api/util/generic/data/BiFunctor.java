package com.dfsek.terra.api.util.generic.data;

import java.util.function.Function;


public interface BiFunctor<T, U, B extends BiFunctor<?, ?, B>> {
    <V> BiFunctor<V, U, B> mapLeft(Function<T, V> map);
    <V> BiFunctor<T, V, B> mapRight(Function<U, V> map);
}
