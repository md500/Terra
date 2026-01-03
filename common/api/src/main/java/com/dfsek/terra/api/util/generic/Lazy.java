/*
 * Copyright (c) 2020-2025 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.terra.api.util.generic;

import com.dfsek.terra.api.util.generic.control.Monad;
import com.dfsek.terra.api.util.generic.data.Functor;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;


public final class Lazy<T> implements Monad<T, Lazy<?>> {
    private final Supplier<T> valueSupplier;
    private volatile T value = null;
    private final AtomicBoolean got = new AtomicBoolean(false);

    private Lazy(Supplier<T> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    public static <T> Lazy<T> lazy(Supplier<T> valueSupplier) {
        return new Lazy<>(valueSupplier);
    }

    public T value() {
        if(!got.compareAndExchange(false, true)) {
            value = valueSupplier.get();
        }
        return value;
    }

    @Override
    public @NotNull <T2> Lazy<T2> bind(@NotNull Function<T, Monad<T2, Lazy<?>>> map) {
        return lazy(() -> ((Lazy<T2>) map.apply(value())).value());
    }

    @Override
    public @NotNull <U> Lazy<U> map(@NotNull Function<T, U> map) {
        return (Lazy<U>) Monad.super.map(map);
    }

    @Override
    public @NotNull <T1> Lazy<T1> pure(@NotNull T1 t) {
        return new Lazy<>(() -> t);
    }
}
