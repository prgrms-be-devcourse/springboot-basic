package org.prgrms.kdt.io;

import java.util.Scanner;

public class InputConsole implements Input{

    private final Scanner scanner;

    public InputConsole() {
        this.scanner = new Scanner(System.in);
    }

    public String inputFunction() {
        return scanner.nextLine();
    }

    public String inputVoucherType() {
        return scanner.nextLine();
    }

    public String inputAmount() {
        System.out.print("Type amount : ");
        return scanner.nextLine();
    }

    @Override
    public String inputCustomerName() {
        System.out.print("input customer name : ");
        return scanner.nextLine();
    }

    @Override
    public String inputCustomerEmail() {
        System.out.print("input customer Email : ");
        return scanner.nextLine();
    }
}
