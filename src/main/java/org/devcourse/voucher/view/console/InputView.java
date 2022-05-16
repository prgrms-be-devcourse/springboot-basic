package org.devcourse.voucher.view.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputView implements Input{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

}
