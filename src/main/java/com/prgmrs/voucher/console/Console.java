package com.prgmrs.voucher.console;

import java.util.Scanner;

public class Console implements Input<String>, Output{

    private Scanner sc;

    public Console(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public String read() {
        return sc.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
