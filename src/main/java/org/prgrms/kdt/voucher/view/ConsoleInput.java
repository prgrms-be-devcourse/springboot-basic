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
}
