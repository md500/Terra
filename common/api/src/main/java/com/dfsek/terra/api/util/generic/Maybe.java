package com.dfsek.terra.api.util.generic;

import java.util.Optional;
import java.util.function.Function;


public interface Maybe<T> extends Monad<T, Maybe<?>> {
    @Override
    <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map);

    @Override
    <T2> Maybe<T2> identity();

    @Override
    <U> Maybe<U> map(Function<T, U> map);

    @Override
    Monad<T, Maybe<?>> multiply(Maybe<?> t);

    default Optional<T> toOptional() {

    }

    record Just<T>(T value) implements Maybe<T> {

    }
}
