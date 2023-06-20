package com.programmers.voucher.view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String getRequest() {
        String userInput = scanner.nextLine();
        return userInput;
    }
}
