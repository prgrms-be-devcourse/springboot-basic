package com.example.kdtspringmission.view;

import java.util.Scanner;

public class ConsoleInputView implements InputView{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String getCommand() {
        return scanner.nextLine();
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
