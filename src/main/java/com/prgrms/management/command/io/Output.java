package com.prgrms.management.command.io;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public void printResult(String output) {
        System.out.println(output);
    }

    public String printCommand() {
        return "=== Voucher Program ===\nType exit to exit the program.\n" +
                "Type create to create a new voucher.\nType list to list all vouchers.\nINPUT:";
    }

    public String printVoucher() {
        return "=== Choose Voucher ===\nType fixed to create a new FixedVoucher.\n" +
                "Type percent to create a new percentVoucher.\nINPUT:";
    }
}
