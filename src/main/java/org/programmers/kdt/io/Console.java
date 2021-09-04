package org.programmers.kdt.io;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String input(String messageToPrint) {
        System.out.println(messageToPrint);
        System.out.print(">>> ");
        return sc.nextLine().trim();
    }

    @Override
    public void inputError(String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public void printSuccessMessage() {
        System.out.println("Your action has been successfully executed.");
    }

    @Override
    public void sayGoodBye() {
        System.out.println("Terminating...");
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

}
