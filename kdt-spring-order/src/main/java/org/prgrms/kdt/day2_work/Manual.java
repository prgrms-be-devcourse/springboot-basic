package org.prgrms.kdt.day2_work;


import org.prgrms.kdt.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public class Manual {

    public static void startProgram(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    public static void exit(){
        System.out.println("Bye~!");
    }

    public static void chooseType() {
        System.out.println("Choose one");
        System.out.println("1.FixedAmountVoucher");
        System.out.println("2.PercentDiscountVoucher");
    }

    public static void inputPercent(){
        System.out.print("Input Percent to under 100 : ");
    }

    public static void inputFixAmount(){
        System.out.print("Input Discount Amount : ");
    }

    public static void listIsEmpty() {
        System.out.println("Voucher list is empty");
    }

    public static void showList(Map<UUID, Voucher> voucherList) {
        for(UUID id : voucherList.keySet())
            System.out.println(voucherList.get(id).toString());
    }
}
