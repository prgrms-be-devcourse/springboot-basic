package org.programmer.kdtspringboot;

import org.programmer.kdtspringboot.io.Input;
import org.programmer.kdtspringboot.io.Output;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String input(String s) {
        System.out.print(s);
        return scanner.nextLine();
    }

    @Override
    public void menu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void print(String content) {
        System.out.println(content);
    }

    @Override
    public void exit() {
        System.out.println("종료합니다");
    }

    @Override
    public void choice() {
        System.out.println("1. Amount\n2. Percent");
    }
}
