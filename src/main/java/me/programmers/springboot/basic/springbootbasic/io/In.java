package me.programmers.springboot.basic.springbootbasic.io;

import java.util.Scanner;

public class In implements ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputCommand(String info) {
        System.out.print(info + " ");
        return scanner.nextLine().toLowerCase();
    }
}
