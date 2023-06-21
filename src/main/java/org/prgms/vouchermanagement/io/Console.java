package org.prgms.vouchermanagement.io;

import org.prgms.vouchermanagement.validator.VoucherInputValidator;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console {

    private final Scanner scanner;
    private final VoucherInputValidator validator;

    public Console(VoucherInputValidator validator) {
        this.validator = validator;
        scanner = new Scanner(System.in);
    }

    public void printCommandMenu() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' to exit the program.");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
        System.out.print("Input: ");
    }

    public void printSelectVoucherType() {
        System.out.println();
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Select 1 or 2: ");
    }

    public String getCommand() {
        return scanner.nextLine();
    }

    public int getVoucherType() {
        String input = scanner.nextLine();
        validator.checkVoucherTypeInput(input);

        return Integer.parseInt(input);
    }
}
