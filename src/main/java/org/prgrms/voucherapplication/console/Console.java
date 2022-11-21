package org.prgrms.voucherapplication.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {

    private static final String ENGLISH_REGEX = "^[a-zA-Z]*$";

    @Override
    public void display(String string) {
        System.out.println(string);
    }

    @Override
    public String command() {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();

        if (inputString.matches(ENGLISH_REGEX)) {
            inputString = inputString.toUpperCase();
        }
        return inputString;
    }
}
