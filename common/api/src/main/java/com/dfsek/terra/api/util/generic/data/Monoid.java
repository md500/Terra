package com.dfsek.terra.api.util.generic.data;

import com.dfsek.terra.api.util.generic.kinds.K;


public interface Monoid<T, M extends Monoid<?, M>> extends Semigroup<T, M>, K<M, T>{
    <T1, M1 extends Monoid<?, M1>> Monoid<T1, M1> identity();

    @Override
    Monoid<T, M> multiply(M t);
}
