package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String input() {
        return scanner.next();
    }

    @Override
    public void printInit() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===\n");
        sb.append("Type exit to exit the program.\n");
        sb.append("Type create to create a new voucher.\n");
        sb.append("Type list to list all vouchers.");
        System.out.println(sb);
    }

    @Override
    public void printTypeChoice() {
        System.out.print("Which type of voucher do you want to make? [fixed | percent] > ");
    }

    @Override
    public void printDiscountAmountChoice() {
        System.out.print("How much discount do you want to get? > ");
    }

    @Override
    public void printVoucher(Voucher voucher) {
        StringBuffer sb = new StringBuffer();
        sb.append("Voucher is just created!");
        sb.append(System.lineSeparator());
        sb.append(MessageFormat.format("Voucher info : {0}", voucher));
        System.out.println(sb);
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        for (var voucher : voucherList) {
            System.out.println(voucher);
        }
    }

    @Override
    public void printExit() {
        System.out.println("Have a Nice Day! :)");
    }

    @Override
    public void printInvalidMessage() {
        System.out.println("Oooops! Please type valid input🙏🏻");
    }
}
