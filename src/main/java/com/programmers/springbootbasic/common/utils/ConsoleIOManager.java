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
                Type blacklist to show all blacklist Consumers.
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
