package org.prgms.order;

import org.prgms.order.io.Input;
import org.prgms.order.io.Output;
import org.prgms.order.voucher.service.VoucherService;

import java.util.Scanner;

 public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String input(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    @Override
    public void mainMenu() {
        System.out.println("\n=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void selectVoucher() {
        System.out.println("\n=== Select Voucher ===");
        System.out.println("Type Fixed to create a new FixedAmountVoucher.");
        System.out.println("Type Percent to create a new PercentDiscountVoucher.");
    }

    @Override
    public void voucherList(VoucherService voucherService) {
        System.out.println("\n=== Vouchers ===");
        System.out.println("         TYPE              AMOUNT");

        voucherService.findAllVoucher().forEach((voucher) ->
                System.out.println(voucher.getVoucherInfo())
        );
    }


 }
