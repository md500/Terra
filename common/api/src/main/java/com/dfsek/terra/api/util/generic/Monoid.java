package com.dfsek.terra.api.util.generic;

import com.dfsek.terra.api.util.generic.kinds.K;


public interface Monoid<T, M extends K<M, T>> extends Semigroup<T, M>{
    <T1, M1 extends K<M1, T1>> Monoid<T1, M1> identity();

    @Override
    Monoid<T, M> multiply(M t);
}
