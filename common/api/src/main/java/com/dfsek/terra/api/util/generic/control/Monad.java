package com.dfsek.terra.api.util.generic.control;

import com.dfsek.terra.api.util.generic.data.Functor;
import com.dfsek.terra.api.util.generic.kinds.K;

import java.util.function.Function;


/**
 * A monad is a monoid in the category of endofunctors.
 */
public interface Monad<T, M extends Monad<?, M>> extends Functor<T, M>, K<M, T> {
    <T2, M2 extends Monad<?, M2>> Monad<T2, M2> bind(Function<T, Monad<T2, M2>> map);

    <T1, M1 extends Monad<?, M1>> Monad<T1, M1> pure(T1 t);

    @Override
    default <U> Monad<U, M> map(Function<T, U> map) {
        return bind(map.andThen(this::pure));
    }

    // almost all well-known applicative functors are also monads, so we can just put that here.
    default <U> Monad<U, M> apply(Monad<Function<T, U>, M> amap) {
        return amap.bind(this::map);
    }
}
