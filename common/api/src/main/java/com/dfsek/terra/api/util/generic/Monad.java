package com.dfsek.terra.api.util.generic;

import java.util.function.Function;


/**
 * A monad is a monoid in the category of endofunctors.
 */
public interface Monad<T, M extends K<M, T>> extends Applicative<T, M>, Monoid<T, M>{
    <T2, M2 extends K<M2, T2>> Monad<T2, M2> bind(Function<T, Monad<T2, M2>> map);
    @Override
    <T2, M2 extends K<M2, T2>> Monad<T2, M2> identity();

    @Override
    <U, M2 extends K<M2, U>> Monad<U, M2> pure(U t);

    @Override
    Monad<T, M> multiply(M t);

    @Override
    default <U, M2 extends K<M2, U>> Monad<U, M2> map(Function<T, U> map) {
        return bind(m -> pure(map.apply(m)));
    }

    @Override
    default <U, A1 extends K<A1, U>> Monad<U, A1> apply(K<M, Function<T, U>> amap) {
        amap.self()
    }
}
