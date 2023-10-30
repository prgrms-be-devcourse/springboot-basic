package org.prgms.springbootbasic.common.console;

import java.util.Collection;
import java.util.Scanner;

public class Console {
    public static final Scanner consoleInput = new Scanner(System.in);

    public static <T> void printList(Collection<T> collection) {
        System.out.println();
        collection.forEach(System.out::println);
        System.out.println();
    }
}
