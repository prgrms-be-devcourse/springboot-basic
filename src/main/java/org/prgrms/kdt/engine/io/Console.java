package org.prgrms.kdt.engine.io;

import org.prgrms.kdt.engine.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputCommand(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void inputError() {
        System.out.println("Wrong Input");
    }

    @Override
    public void createVoucher(Voucher voucher) {
        System.out.println("Created Voucher " + voucher);
    }

    @Override
    public void listVoucher(Optional<List<Voucher>> voucherList) {
        if (voucherList.isEmpty()) {
            System.out.println("No Vouchers Found");
            return;
        }

        List<Voucher> vouchers = voucherList.get();
        for (Voucher voucher : vouchers)
            System.out.println(voucher);
    }
}
