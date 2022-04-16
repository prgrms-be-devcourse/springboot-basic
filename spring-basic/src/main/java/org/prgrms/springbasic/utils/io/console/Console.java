package org.prgrms.springbasic.utils.io.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {

    @Override
    public String takeInput() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    @Override
    public void printToConsole(Object object) {
        System.out.println(object.toString());
    }
}
