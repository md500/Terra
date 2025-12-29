package com.dfsek.terra.api.util.generic;

import com.dfsek.terra.api.util.generic.kinds.K;

import java.util.function.Function;


public interface Applicative<T, A extends K<A, T>> extends Functor<T> {
    <U, A1 extends K<A1, U>> Applicative<U, A1> pure(U t);

    <U, A1 extends K<A1, U>> Applicative<U, A1> apply(K<A, Function<T, U>> amap);
}
