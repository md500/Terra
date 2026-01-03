package com.dfsek.terra.api.util.function;

import com.dfsek.terra.api.util.generic.data.types.Either;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;


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
}
