package org.programmers.springorder.global.handler;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExceptionHandler {

    private ExceptionHandler() {
    }

    public static <T> T input(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            printExceptionMessage(e);
            return input(supplier);
        }
    }

    public static <T> void process(Consumer<T> consumer, T t) {
        try {
            consumer.accept(t);
        } catch (IllegalArgumentException e) {
            printExceptionMessage(e);
            process(consumer, t);
        }
    }

    private static void printExceptionMessage(RuntimeException e) {
        System.out.println(e.getMessage());
    }
}

