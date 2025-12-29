package com.dfsek.terra.api.util.generic;

import java.util.function.Function;


/**
 * A monad is a monoid in the category of endofunctors.
 */
public interface Monad<T, M extends Monad<?, M>> extends Functor<T, M>, Monoid<T, M> {
    <T2> Monad<T2, M> bind(Function<T, Monad<T2, M>> map);
    <T2> Monad<T2, M> pure(T2 t);

    @Override
    default <U> Monad<U, M> map(Function<T, U> map) {
        return bind(m -> pure(map.apply(m)));
    }
}
