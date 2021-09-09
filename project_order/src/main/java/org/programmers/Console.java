package org.programmers;

import java.io.*;

public class Console implements Input, Output {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public String input() throws IOException {
        return br.readLine();
    }

    @Override
    public void printPrompt() throws IOException {
        bw.write("=== Voucher Program ===\n");
        bw.write("Type exit to exit the program.\n");
        bw.write("Type create to create a new voucher.\n");
        bw.write("Type list to list all vouchers.\n");
        bw.write("Type blacklist to list all customers on blacklist.\n");
        bw.flush();
    }

    @Override
    public void printSign() throws IOException {
        bw.write("> ");
        bw.flush();
    }

    @Override
    public void printVoucherTypes() throws IOException {
        bw.write("Type fixed to create fixed amount voucher\n");
        bw.write(("Type percent to create percent discount voucher\n"));
        bw.flush();
    }

    @Override
    public void askAmount() throws IOException {
        bw.write(("How much do you want to discount?\n"));
        bw.flush();
    }

    @Override
    public void askPercentage() throws IOException {
        bw.write(("What percentage do you want to discount?\n"));
        bw.flush();
    }

}
