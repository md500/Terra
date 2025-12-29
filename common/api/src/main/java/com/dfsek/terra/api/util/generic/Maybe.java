package com.dfsek.terra.api.util.generic;

import java.util.Optional;


public interface Maybe<T> {
    default Optional<T> toOptional() {

    }
}
