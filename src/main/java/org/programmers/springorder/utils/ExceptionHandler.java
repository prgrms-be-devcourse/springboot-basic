package org.programmers.springorder.utils;

import java.util.function.Supplier;

public class ExceptionHandler {

    public static <T> T input(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            printExceptionMessage(e);
            return input(supplier);
        }
    }

    private static void printExceptionMessage(RuntimeException e) {
        System.out.println(e.getMessage());
    }
}

