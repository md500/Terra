package com.dfsek.terra.api.util.generic.data.types;

import com.dfsek.terra.api.util.generic.control.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


public interface Maybe<T> extends Monad<T, Maybe<?>> {
    @Override
    default <T1> Maybe<T1> pure(T1 t) {
        return just(t);
    }

    @Override
    <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map);

    Optional<T> toOptional();

    <L> Either<L, T> toEither(L l);
    T get(Supplier<T> def);

    default T get(T def) {
        return get(() -> def);
    }

    default <T1> Maybe<T1> overwrite(Maybe<T1> m) {
        return bind(ignore -> m);
    }

    static <T1> Maybe<T1> just(T1 t) {
        record Just<T>(T value) implements Maybe<T> {

            @Override
            public Optional<T> toOptional() {
                return Optional.of(value);
            }

            @Override
            public <L> Either<L, T> toEither(L l) {
                return Either.right(value);
            }

            @Override
            public T get(Supplier<T> def) {
                return value;
            }

            @Override
            public <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map) {
                return (Maybe<T2>) map.apply(value);
            }
        }
        return new Just<>(t);
    }

    static <T1> Maybe<T1> nothing() {
        record Nothing<T>() implements Maybe<T> {

            @Override
            public <T2> Maybe<T2> bind(Function<T, Monad<T2, Maybe<?>>> map) {
                return nothing();
            }

            @Override
            public Optional<T> toOptional() {
                return Optional.empty();
            }

            @Override
            public <L> Either<L, T> toEither(L l) {
                return Either.left(l);
            }

            @Override
            public T get(Supplier<T> def) {
                return def.get();
            }
        }
        return new Nothing<>();
    }
}
