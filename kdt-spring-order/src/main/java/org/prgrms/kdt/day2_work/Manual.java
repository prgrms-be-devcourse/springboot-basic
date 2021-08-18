package org.prgrms.kdt.day2_work;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Manual {

    public void startProgram(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    public void exit(){
        System.out.println("Bye~!");
    }

    public void chooseType() {
        System.out.println("Choose one");
        System.out.println("1.FixedAmountVoucher");
        System.out.println("2.PercentDiscountVoucher");
    }

    public void inputPercent(){
        System.out.print("Input Percent to under 100 : ");
    }

    public void inputFixAmount(){
        System.out.print("Input Discount Amount : ");
    }
}
