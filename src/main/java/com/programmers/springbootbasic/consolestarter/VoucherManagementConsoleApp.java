package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.service.VoucherService;

import java.util.Scanner;

public class VoucherManagementApp implements ConsoleApp {

    private final Scanner sc = new Scanner(System.in);

    private VoucherService voucherService;

    public VoucherManagementApp(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        while(true) {
            String input = sc.next();


        }
    }

    private void showMenu() {

    }

}
