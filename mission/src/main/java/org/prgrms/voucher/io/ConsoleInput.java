package org.prgrms.voucher.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public String getString() {
        return sc.nextLine();
    }
}