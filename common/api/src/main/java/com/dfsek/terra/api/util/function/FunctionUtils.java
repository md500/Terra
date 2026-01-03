package com.dfsek.terra.api.util.function;

import com.dfsek.terra.api.util.generic.data.types.Either;

import com.dfsek.terra.api.util.generic.data.types.Pair;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public final class FunctionUtils {
    private FunctionUtils() { }

    @Contract("_ -> new")
    public static <T> @NotNull Function<T, T> lift(@NotNull Consumer<T> c) {
        Objects.requireNonNull(c);
        return co -> {
            c.accept(co);
            return co;
        };
    }

    @Contract("_ -> fail")
    public static <T extends Throwable, U> @NotNull U throw_(@NotNull T e) throws T {
        throw e;
    }

    @SuppressWarnings("unchecked")
    @Contract("_ -> fail")
    public static <E extends Throwable, U> @NotNull U sneakyThrow(@NotNull Throwable e) throws E {
        throw (E) e;
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T, U> @NotNull Function<T, Either<Exception, U>> liftTry(@NotNull Function<T, U> f) {
        return s -> {
            try {
                return Either.right(f.apply(s));
            } catch(Exception e) {
                return Either.left(e);
            }
        };
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T, U> @NotNull Function<T, Either<Throwable, U>> liftTryUnsafe(@NotNull Function<T, U> f) {
        return s -> {
            try {
                return Either.right(f.apply(s));
            } catch(Throwable e) {
                return Either.left(e);
            }
        };
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T, U, R> @NotNull Function<Pair<T, U>, R> tuple(@NotNull BiFunction<T, U, R> f) {
        return p -> f.apply(p.left(), p.right());
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T, U, R> @NotNull BiFunction<T, U, R> untuple(@NotNull Function<Pair<T, U>, R> f) {
        return (a, b) -> f.apply(Pair.of(a, b));
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T, U, R> @NotNull Function<T, Function<U, R>> curry(@NotNull BiFunction<T, U, R> f) {
        return a -> b -> f.apply(a, b);
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T, U, R> @NotNull BiFunction<T, U, R> uncurry(@NotNull Function<T, Function<U, R>> f) {
        return (a, b) -> f.apply(a).apply(b);
    }

    @Contract(pure = true, value = "_ -> new")
    public static <T> @NotNull T construct(@NotNull Supplier<T> in) {
        return Objects.requireNonNull(Objects.requireNonNull(in).get());
    }
}
