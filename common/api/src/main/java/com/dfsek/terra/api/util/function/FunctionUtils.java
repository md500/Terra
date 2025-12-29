package com.dfsek.terra.api.util.function;

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

}
