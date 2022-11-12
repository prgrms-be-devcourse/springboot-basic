package com.program.commandLine.io;

import com.program.commandLine.customer.Customer;
import com.program.commandLine.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ScannerOutput implements Output {

    static final String NEWLINE = System.lineSeparator();

    @Override
    public void menuView() {
        System.out.println("=== Voucher Program ===" + NEWLINE +
                "Type exit to exit the program." + NEWLINE +
                "Type create to create a new voucher." + NEWLINE +
                "Type list to list all vouchers." + NEWLINE +
                "Type blacklist to view all blacklist customer.");
    }

    @Override
    public void messageView(String message) {
        System.out.println(message + "\n");
    }


    @Override
    public void allVoucherView(List<Voucher> vouchers) {
        vouchers.forEach(value -> {
            String voucher = value.getVoucherType() + " voucher - " + value.getVoucherDiscount();
            System.out.println(value.getVoucherId() + " : " + voucher);
        });
        System.out.println();
    }

    @Override
    public void customerBlackListView(List<Customer> blackList) {
        System.out.println("--------------------");
        blackList.forEach(customer -> {
            System.out.println(customer.getCustomerId() + " - " + customer.getName());
        });
        System.out.println("--------------------");
    }
}
