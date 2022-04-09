package org.prgms.io;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements InOut {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void optionMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void inputError() {
        System.out.println("입력이 올바르지 않습니다. 다시 입력해주세요.");
    }

    @Override
    public int chooseVoucher() {
        System.out.println("which one to create : 1. FixedAmountVoucher,  2. PercentDiscountVoucher");
        String opt = scanner.nextLine();
        return Integer.parseInt(opt);
    }
}
