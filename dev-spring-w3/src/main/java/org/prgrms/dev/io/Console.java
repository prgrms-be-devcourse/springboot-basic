package org.prgrms.dev.io;

import org.prgrms.dev.voucher.service.VoucherService;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void init() {
        System.out.println("=== Voucher Program ===\n" +
                "Type [exit] to exit the program.\n" +
                "Type [create] to create a new voucher.\n" +
                "Type [list] to list all vouchers.");
    }

    @Override
    public void voucherSelectType() {
        System.out.print("=== Select Voucher Type ===\n" +
                "fixed amount [f] | percent discount [p] ");
    }

    @Override
    public void voucherList(VoucherService voucherService) {
        System.out.println(voucherService.listVoucher().toString());
    }

}
