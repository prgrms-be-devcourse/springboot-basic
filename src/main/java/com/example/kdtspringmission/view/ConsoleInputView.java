package com.example.kdtspringmission.view;

import com.example.kdtspringmission.Command;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInputView implements InputView{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public Command getCommand() {
        String input = scanner.nextLine();
        return Command.valueOf(input.toUpperCase());
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }
}
