package com.dfsek.terra.api.util.generic.data.types;

import com.dfsek.terra.api.util.generic.control.Monad;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;


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

    boolean isJust();

    @Override
    <U> Maybe<U> map(Function<T, U> map);

    default T get(T def) {
        return get(() -> def);
    }

    default <T1> Maybe<T1> overwrite(Maybe<T1> m) {
        return bind(ignore -> m);
    }

    Maybe<T> or(Supplier<Maybe<T>> or);

    default Maybe<T> or(Maybe<T> or) {
        return or(() -> or);
    }

    default Stream<T> toStream() {
        return map(Stream::of).get(Stream.empty());
    }

    default Maybe<T> filter(Predicate<T> filter) {
        return bind(o -> filter.test(o) ? this : nothing());
    }

    static <T> Maybe<T> fromOptional(Optional<T> op) {
        return op.map(Maybe::just).orElseGet(Maybe::nothing);
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
            public boolean isJust() {
                return true;
            }

            @Override
            public <U> Maybe<U> map(Function<T, U> map) {
                return just(map.apply(value));
            }

            @Override
            public Maybe<T> or(Supplier<Maybe<T>> or) {
                return this;
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

            @Override
            public boolean isJust() {
                return false;
            }

            @Override
            @SuppressWarnings("unchecked")
            public <U> Maybe<U> map(Function<T, U> map) {
                return (Maybe<U>) this;
            }

            @Override
            public Maybe<T> or(Supplier<Maybe<T>> or) {
                return or.get();
            }
        }
        return new Nothing<>();
    }
}
