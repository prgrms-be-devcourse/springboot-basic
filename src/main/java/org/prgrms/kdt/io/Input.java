package org.prgrms.kdt.io;

import java.util.Scanner;

public class Input {
    private final Scanner sc;

    public Input() {
        this.sc = new Scanner(System.in);
    }

    public String inputFunction() {
        return sc.nextLine();
    }

    public String voucherType() {
        return sc.nextLine();
    }

    public String amount() {
        System.out.print("Type amount : ");
        return sc.nextLine();
    }

    public Scanner getSc() {
        return sc;
    }
}
