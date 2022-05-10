package com.kdt.commandLineApp.io;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class commandLineAppIO implements IO {
    Scanner scanner = new Scanner(System.in);

    @Override
    public String get() throws Exception {
        String input;
        input = scanner.nextLine();
        return input;
    }

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public void print(List msgs) {
        msgs.stream().forEach(System.out::println);
    }
}
