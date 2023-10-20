package com.programmers.springbootbasic.common.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConsoleIOManager {
    private final BufferedReader bufferedReader;

    public ConsoleIOManager() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void print(String s) {
        System.out.print(s);
    }

    public void println(String s) {
        System.out.println(s);
    }

    public void printProgramSelectMenu() {
        String selectMenu = """
                === Program Select ===
                Type exit to exit the program.
                Type voucher to select Voucher program.
                Type customer to select Customer program.
                """;
        println(selectMenu);
    }

    public void printVoucherProgramMenu() {
        String programMenu = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type find to find voucher by voucher ID.
                Type update to update voucher value.
                Type delete to delete voucher.
                Type deleteAll to delete all vouchers.
                Type customer to show customers list which voucher have.
                """;
        println(programMenu);
    }

    public void printVoucherSelectMenu() {
        String voucherMenu = """
                === Select Voucher ===
                Type 1 to select Fixed Amount Voucher.
                Type 2 to select Percent Discount Voucher.
                """;
        println(voucherMenu);
    }

    public void printCustomerProgramMenu() {
        String programMenu = """
                === Customer Program ===
                Type exit to exit the program.
                Type create to create customer.
                Type list to list all customers.
                Type blacklist to show all blacklist customers.
                Type addBlacklist to add customer in blacklist.
                Type removeBlacklist to remove customer from blacklist.
                Type deleteAll to delete all customers.
                Type addVoucher to add voucher into customer's wallet.
                Type removeVoucher to remove voucher from customer's wallet.
                Type wallet to show customer's wallet.
                """;
        println(programMenu);
    }

    public void printSystemMsg(String s) {
        println("[System] %s\n".formatted(s));
    }

    public String getInput() throws IOException {
        print("> ");
        String input = bufferedReader.readLine();
        print("\n");
        return input;
    }
}
