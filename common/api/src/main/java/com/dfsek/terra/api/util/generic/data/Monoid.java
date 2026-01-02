package com.dfsek.terra.api.util.generic.data;

import com.dfsek.terra.api.util.generic.kinds.K;


public interface Monoid<T, M extends Monoid<?, M>> extends K<M, T>{
    <T1> Monoid<T1, M> identity();

    Monoid<T, M> multiply(Monoid<T, M> t);
}
