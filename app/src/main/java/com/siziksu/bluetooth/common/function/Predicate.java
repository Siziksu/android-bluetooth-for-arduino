package com.siziksu.bluetooth.common.function;

@FunctionalInterface
public interface Predicate<O> {

    void supply(O object);
}
