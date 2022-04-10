package com.prgrms.management.command.io;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public void printResult(String output) {
        System.out.println(output);
    }

    public String InputCommand() {
        return "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
    }

    public String InputVoucher() {
        return "=== Choose Voucher ===\nType fixed to create a new FixedVoucher.\n" +
                "Type percent to create a new percentVoucher.";
    }
}
