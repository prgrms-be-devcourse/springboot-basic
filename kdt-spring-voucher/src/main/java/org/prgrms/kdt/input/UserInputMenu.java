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

    @Override
    public String userInputVoucherCreateMenuCommand() {
        return scanner.nextLine().trim();
    }

    @Override
    public long userInputVoucherValue() {
        long voucherValue = scanner.nextLong();
        scanner.nextLine();
        return voucherValue;
    }
}
