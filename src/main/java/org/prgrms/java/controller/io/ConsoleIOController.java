package org.prgrms.java.controller.io;

import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class ConsoleIOController implements IOController {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void write(Object object) {
        System.out.println(object.toString());
    }
}
