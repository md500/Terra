package com.dfsek.terra.api.util.function;

import com.dfsek.terra.api.util.generic.data.types.Either;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public final class FunctionUtils {
    private FunctionUtils() {}

    public static <T> Function<T, T> lift(Consumer<T> c) {
        return co -> {
            c.accept(co);
            return co;
        };
    }

    @SuppressWarnings("unchecked")
    public static <T, L> Either<L, T> toEither(Optional<T> o, L de) {
        return (Either<L, T>) o.map(Either::right).orElseGet(() -> Either.left(de));
    }

    public static <T> T collapse(Either<T, T> either) {
        return either.collect(Function.identity(), Function.identity());
    }

    public static <T extends Throwable, U> U throw_(T e) throws T {
        throw e;
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable, U> U sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    public static <T, U> Function<T, Either<Exception, U>> liftTry(Function<T, U> f) {
        return s -> {
            try {
                return Either.right(f.apply(s));
            } catch(Exception e) {
                return Either.left(e);
            }
        };
    }

}
