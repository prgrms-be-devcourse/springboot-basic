package org.prgrms.kdt.io;

import java.util.Scanner;

public class InputConsole implements Input{

    private final Scanner sc;

    public InputConsole() {
        this.sc = new Scanner(System.in);
    }

    public String function() {
        return sc.nextLine();
    }

    public String voucherType() {
        return sc.nextLine();
    }

    public String amount() {
        System.out.print("Type amount : ");
        return sc.nextLine();
    }
}
