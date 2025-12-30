package com.dfsek.terra.api.util.generic.data.types;

import com.dfsek.terra.api.util.generic.control.Monad;

import java.util.Optional;
import java.util.function.Function;


public interface Maybe<T> extends Monad<T, Maybe<?>> {
    @Override
    default <T1> Maybe<T1> pure(T1 t) {
        return just(t);
    }

    @Override
    <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map);

    Optional<T> toOptional();

    static <T1> Maybe<T1> just(T1 t) {
        return new Just<>(t);
    }

    static <T1> Maybe<T1> nothing() {
        return new Nothing<>();
    }

    record Just<T>(T value) implements Maybe<T> {

        @Override
        public Optional<T> toOptional() {
            return Optional.of(value);
        }

        @Override
        public <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map) {
            return (Maybe<T2>) map.apply(value);
        }
    }
    record Nothing<T>() implements Maybe<T> {

        @Override
        public <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map) {
            return nothing();
        }

        @Override
        public Optional<T> toOptional() {
            return Optional.empty();
        }
    }
}
