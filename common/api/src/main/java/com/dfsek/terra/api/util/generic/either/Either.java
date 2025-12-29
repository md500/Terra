/*
 * Copyright (c) 2020-2025 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.terra.api.util.generic.either;

import com.dfsek.terra.api.util.function.FunctionUtils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public interface Either<L, R> {
    default Either<L, R> ifLeft(Consumer<L> action) {
        return mapLeft(FunctionUtils.lift(action));
    }
    default Either<L, R> ifRight(Consumer<R> action) {
        return mapRight(FunctionUtils.lift(action));
    }

    <L1> Either<L1, R> mapLeft(Function<L, L1> f);
    <R1> Either<L, R1> mapRight(Function<R, R1> f);

    Optional<L> getLeft();
    Optional<R> getRight();
    boolean isLeft();
    boolean isRight();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @NotNull
    @Contract("_ -> new")
    static <L1, R1> Either<L1, R1> left(L1 left) {
        return new Left(Objects.requireNonNull(left));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @NotNull
    @Contract("_ -> new")
    static <L1, R1> Either<L1, R1> right(R1 right) {
        return new Right(Objects.requireNonNull(right));
    }
    record Left<T>(T value) implements Either<T, Void> {

        @Override
        public <L1> Either<L1, Void> mapLeft(Function<T, L1> f) {
            return new Left<>(f.apply(value));
        }

        @SuppressWarnings({ "unchecked" })
        @Override
        public <R1> Either<T, R1> mapRight(Function<Void, R1> f) {
            return (Either<T, R1>) this;
        }

        @Override
        public Optional<T> getLeft() {
            return Optional.of(value);
        }

        @Override
        public Optional<Void> getRight() {
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
    }
    record Right<T>(T value) implements Either<Void, T> {
        @SuppressWarnings({ "unchecked" })
        @Override
        public <L1> Either<L1, T> mapLeft(Function<Void, L1> f) {
            return (Either<L1, T>) this;
        }

        @Override
        public <R1> Either<Void, R1> mapRight(Function<T, R1> f) {
            return new Right<>(f.apply(value));
        }

        @Override
        public Optional<Void> getLeft() {
            return Optional.empty();
        }

        @Override
        public Optional<T> getRight() {
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
    }
}