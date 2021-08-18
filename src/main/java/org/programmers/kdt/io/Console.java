package org.programmers.kdt.io;

import org.programmers.kdt.voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.println(prompt);
        System.out.print(">>> ");
        return sc.nextLine();
    }

    @Override
    public void inputError(String message) {
        System.out.println(message);
    }

    @Override
    public void printSuccessAddVoucher(Voucher voucher) {
        System.out.println("** Your new voucher has been added **");
        System.out.println(voucher);
    }

    @Override
    public void sayGoodBye() {
        System.out.println("Terminating...");
    }

    @Override
    public void printAllVouchersInfo(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println(voucher);
        }
    }
}
