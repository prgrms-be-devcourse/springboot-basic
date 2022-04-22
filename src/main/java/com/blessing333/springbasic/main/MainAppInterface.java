package com.blessing333.springbasic.main;

import com.blessing333.springbasic.common.ui.UserInterface;

import java.util.Scanner;

public class MainAppInterface implements UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String inputMessage() {
        return scanner.nextLine();
    }

    @Override
    public void showGuideText() {
        printMessage("1. 고객 관리");
        printMessage("2. 바우처 관리");
    }
}
