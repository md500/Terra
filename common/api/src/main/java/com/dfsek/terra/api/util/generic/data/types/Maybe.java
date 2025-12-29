package com.dfsek.terra.api.util.generic.data.types;

import com.dfsek.terra.api.util.generic.control.Monad;

import java.util.Optional;
import java.util.function.Function;


public interface Maybe<T>{

    Optional<T> toOptional();

    record Just<T>(T value) implements Maybe<T> {

        @Override
        public Optional<T> toOptional() {
            return Optional.of(value);
        }
    }
    record Nothing<T>() implements Maybe<T> {

        @Override
        public Optional<T> toOptional() {
            return Optional.empty();
        }
    }
}
