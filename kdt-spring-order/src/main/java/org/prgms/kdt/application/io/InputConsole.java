package org.prgms.kdt.application.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputConsole implements Input{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String typeOptionInput() {
        return scanner.nextLine();
    }
}
