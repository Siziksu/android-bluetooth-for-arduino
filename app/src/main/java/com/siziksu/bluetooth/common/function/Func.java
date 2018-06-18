package com.siziksu.bluetooth.common.function;

import java.util.List;

public class Func {

    @FunctionalInterface
    public interface Predicate<T> {

        boolean test(T t);
    }

    @FunctionalInterface
    public interface Consumer<T> {

        void accept(T t);
    }

    public static <T> void apply(List<T> list, Consumer<T> consumer) {
        for (int i = 0; i < list.size(); i++) {
            consumer.accept(list.get(i));
        }
    }

    public static <T> void apply(List<T> list, Predicate<T> action, Consumer<T> consumer) {
        for (int i = 0; i < list.size(); i++) {
            if (action.test(list.get(i))) {
                consumer.accept(list.get(i));
            }
        }
    }
}
