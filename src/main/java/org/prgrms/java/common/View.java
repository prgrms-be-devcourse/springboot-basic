package org.prgrms.java.common;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class View {
    private final Scanner scanner = new Scanner(System.in);

    public String read() {
        return scanner.nextLine();
    }

    public void print(Object object) {
        System.out.println(object.toString());
    }
}
