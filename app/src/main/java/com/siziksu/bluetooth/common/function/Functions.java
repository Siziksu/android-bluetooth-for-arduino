package com.siziksu.bluetooth.common.function;

import java.util.List;

public class Functions {

    @FunctionalInterface
    public interface Predicate<T> {

        boolean test(T t);
    }

    @FunctionalInterface
    public interface Consumer<T> {

        void accept(T t);
    }

    public static <T> void apply(List<T> list, Functions.Predicate<T> action, Functions.Consumer<T> consumer) {
        for (int i = 0; i < list.size(); i++) {
            if (action.test(list.get(i))) {
                consumer.accept(list.get(i));
            }
        }
    }
}
