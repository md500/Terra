package com.dfsek.terra.api.util.generic.data;

import com.dfsek.terra.api.util.generic.data.types.Pair;

import java.util.function.Consumer;
import java.util.function.Function;


public interface BiFunctor<T, U, B extends BiFunctor<?, ?, B>> {
    static <L, R, B extends BiFunctor<?, ?, B>> Consumer<BiFunctor<L, R, B>> consumeLeft(Consumer<L> consumer) {
        return pair -> pair.mapLeft(p -> {
            consumer.accept(p);
            return p;
        });
    }

    static <L, R> Consumer<Pair<L, R>> consumeRight(Consumer<R> consumer) {
        return pair -> pair.mapRight(p -> {
            consumer.accept(p);
            return p;
        });
    }

    <V> BiFunctor<V, U, B> mapLeft(Function<T, V> map);
    <V> BiFunctor<T, V, B> mapRight(Function<U, V> map);

    default <V, W> BiFunctor<V, W, B> bimap(Function<T, V> left, Function<U, W> right) {
        return mapLeft(left).mapRight(right);
    }
}
