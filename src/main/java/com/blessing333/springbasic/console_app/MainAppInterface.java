package com.blessing333.springbasic.console_app;

import com.blessing333.springbasic.console_app.ui.UserInterface;

import java.util.Scanner;

public class MainAppInterface implements UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String requestMessage() {
        return scanner.nextLine();
    }

    @Override
    public void printGuide() {
        printMessage("1. 고객 관리");
        printMessage("2. 바우처 관리");
    }
}
