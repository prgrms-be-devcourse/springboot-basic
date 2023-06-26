package org.prgrms.kdt.voucher.view;

import java.util.Scanner;

public class ConsoleInput implements Input{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getCommandInput() {
        return scanner.nextLine();
    }

    @Override
    public String getVoucherInput() {
        return scanner.nextLine();
    }

    @Override
    public Long getBenefit() {
        Long benefit = scanner.nextLong();
        flush();
        return benefit;
    }

    private void flush() {
        scanner.nextLine();
    }
}
