package com.dfsek.terra.api.util.generic.data;

import com.dfsek.terra.api.util.generic.control.Monad;
import com.dfsek.terra.api.util.generic.data.types.Maybe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


public sealed interface LinkedList<T> extends Monad<T, LinkedList<?>>, Monoid<T, LinkedList<?>> {
    @Override
    <T2> LinkedList<T2> bind(Function<T, Monad<T2, LinkedList<?>>> map);

    @Override
    default <T1> LinkedList<T1> pure(T1 t) {
        return new Cons<>(t, empty());
    }

    @Override
    <U> LinkedList<U> map(Function<T, U> map);

    @Override
    default <T1> LinkedList<T1> identity() {
        return empty();
    }

    default Maybe<T> head() {
        return get(0);
    }

    int length();

    Maybe<T> get(int index);

    LinkedList<T> add(T value);

    <C extends Collection<T>> C toCollection(C collection);

    default List<T> toList() {
        return toCollection(new ArrayList<>());
    }

    default Set<T> toSet() {
        return toCollection(new HashSet<>());
    }

    @Override
    LinkedList<T> multiply(Monoid<T, LinkedList<?>> t);

    static <T> LinkedList<T> of(T value) {
        return new Cons<>(value, empty());
    }

    @SuppressWarnings("unchecked")
    static <T> Nil<T> empty() {
        return (Nil<T>) Nil.INSTANCE;
    }

    record Cons<T>(T value, LinkedList<T> tail) implements LinkedList<T> {
        @Override
        public <T2> LinkedList<T2> bind(Function<T, Monad<T2, LinkedList<?>>> map) {
            return ((LinkedList<T2>) map.apply(value)).multiply(tail.bind(map));
        }

        @Override
        public <U> LinkedList<U> map(Function<T, U> map) {
            return new Cons<>(map.apply(value), tail.map(map));
        }

        @Override
        public int length() {
            return 1 + tail.length();
        }

        @Override
        public Maybe<T> get(int index) {
            if(index == 0) return Maybe.just(value);
            if(index > 0) return Maybe.nothing();
            return tail.get(index - 1);
        }

        @Override
        public LinkedList<T> add(T value) {
            return new Cons<>(value, tail.add(value));
        }

        @Override
        public <C extends Collection<T>> C toCollection(C collection) {
            collection.add(value);
            return tail.toCollection(collection);
        }

        @Override
        public LinkedList<T> multiply(Monoid<T, LinkedList<?>> t) {
            return new Cons<>(value, tail.multiply(t));
        }
    }

    record Nil<T>() implements LinkedList<T> {
        private static final Nil<?> INSTANCE = new Nil<>();

        @Override
        @SuppressWarnings("unchecked")
        public <T2> LinkedList<T2> bind(Function<T, Monad<T2, LinkedList<?>>> map) {
            return (LinkedList<T2>) this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <U> LinkedList<U> map(Function<T, U> map) {
            return (LinkedList<U>) this;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public Maybe<T> get(int index) {
            return Maybe.nothing();
        }

        @Override
        public LinkedList<T> add(T value) {
            return new Cons<>(value, empty());
        }

        @Override
        public <C extends Collection<T>> C toCollection(C collection) {
            return collection;
        }

        @Override
        public LinkedList<T> multiply(Monoid<T, LinkedList<?>> t) {
            return (LinkedList<T>) t;
        }
    }
}
