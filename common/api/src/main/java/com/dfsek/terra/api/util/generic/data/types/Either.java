/*
 * Copyright (c) 2020-2025 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.terra.api.util.generic.data.types;

import com.dfsek.terra.api.util.function.FunctionUtils;

import com.dfsek.terra.api.util.generic.control.Monad;

import com.dfsek.terra.api.util.generic.data.BiFunctor;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public interface Either<L, R> extends Monad<R, Either<?, ?>>, BiFunctor<L, R, Either<?, ?>> {
    default Either<L, R> ifLeft(Consumer<L> action) {
        return mapLeft(FunctionUtils.lift(action));
    }

    default Either<L, R> ifRight(Consumer<R> action) {
        return mapRight(FunctionUtils.lift(action));
    }

    // Either is a functor in its right parameter.
    @Override
    default <U> Either<L, U> map(Function<R, U> map) {
        return mapRight(map);
    }

    @Override
    default <T1> Either<?, T1> pure(T1 t) {
        return right(t);
    }

    @Override
    <T2> Either<L, T2> bind(Function<R, Monad<T2, Either<?, ?>>> map);

    @Override
    <L1> Either<L1, R> mapLeft(Function<L, L1> f);

    @Override
    <R1> Either<L, R1> mapRight(Function<R, R1> f);

    Optional<L> getLeft();

    Optional<R> getRight();

    boolean isLeft();

    boolean isRight();

    Either<R, L> flip();

    <U> U collect(Function<L, U> left, Function<R, U> right);

    @SuppressWarnings({ "unchecked" })
    @NotNull
    @Contract("_ -> new")
    static <L1, R1> Either<L1, R1> left(L1 left) {
        record Left<L, R>(L value) implements Either<L, R> {

            @Override
            @SuppressWarnings("unchecked")
            public <T2> Either<L, T2> bind(Function<R, Monad<T2, Either<?, ?>>> map) {
                return (Either<L, T2>) this;
            }

            @Override
            public <L1> Either<L1, R> mapLeft(Function<L, L1> f) {
                return new Left<>(f.apply(value));
            }

            @SuppressWarnings({ "unchecked" })
            @Override
            public <R1> Either<L, R1> mapRight(Function<R, R1> f) {
                return (Either<L, R1>) this;
            }

            @Override
            public Optional<L> getLeft() {
                return Optional.of(value);
            }

            @Override
            public Optional<R> getRight() {
                return Optional.empty();
            }

            @Override
            public boolean isLeft() {
                return true;
            }

            @Override
            public boolean isRight() {
                return false;
            }

            @Override
            public Either<R, L> flip() {
                return right(value);
            }

            @Override
            public <U> U collect(Function<L, U> left, Function<R, U> right) {
                return left.apply(value);
            }
        }

        return new Left<>(Objects.requireNonNull(left));
    }

    @SuppressWarnings({"unchecked" })
    @NotNull
    @Contract("_ -> new")
    static <L1, R1> Either<L1, R1> right(R1 right) {
        record Right<L, R>(R value) implements Either<L, R> {
            @Override
            public <T2> Either<L, T2> bind(Function<R, Monad<T2, Either<?, ?>>> map) {
                return (Either<L, T2>) map.apply(value);
            }

            @SuppressWarnings({ "unchecked" })
            @Override
            public <L1> Either<L1, R> mapLeft(Function<L, L1> f) {
                return (Either<L1, R>) this;
            }

            @Override
            public <R1> Either<L, R1> mapRight(Function<R, R1> f) {
                return new Right<>(f.apply(value));
            }

            @Override
            public Optional<L> getLeft() {
                return Optional.empty();
            }

            @Override
            public Optional<R> getRight() {
                return Optional.of(value);
            }

            @Override
            public boolean isLeft() {
                return false;
            }

            @Override
            public boolean isRight() {
                return true;
            }

            @Override
            public Either<R, L> flip() {
                return left(value);
            }

            @Override
            public <U> U collect(Function<L, U> left, Function<R, U> right) {
                return right.apply(value);
            }
        }
        return new Right<>(Objects.requireNonNull(right));
    }

}