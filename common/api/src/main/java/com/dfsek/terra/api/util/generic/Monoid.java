package com.dfsek.terra.api.util.generic;

public interface Monoid<T, M extends Monoid<?, M>> extends Semigroup<T, M>{
    <T1> Monoid<T1, M> pure(T1 t);

    @Override
    Monoid<T, M> multiply(M t);
}
