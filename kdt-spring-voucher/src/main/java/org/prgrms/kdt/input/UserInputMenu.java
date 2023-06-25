package org.prgrms.kdt.input;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserInputMenu implements UserInput{
    Scanner scanner = new Scanner(System.in);
    @Override
    public String userInputMenuCommand() {
        return scanner.nextLine().trim();
    }
}
