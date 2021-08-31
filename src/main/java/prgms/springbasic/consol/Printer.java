package prgms.springbasic.consol;

import prgms.springbasic.voucher.Voucher;

import java.util.List;

public class Printer {
    public void printCommandList() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        printInputSign();
    }

    public void printVoucherTypeList() {
        System.out.println();
        System.out.println("=== Enter the number of voucher type ===");
        System.out.println("1) FixedAmountVoucher");
        System.out.println("2) PercentDiscountVoucher");
        printInputSign();
    }

    public void printAmountInputInfo() {
        System.out.println();
        System.out.println("=== Enter the discount amount ===");
        printInputSign();
    }

    public void printPercentInputInfo() {
        System.out.println();
        System.out.println("=== Enter the discount percent ===");
        printInputSign();
    }

    public void printVoucherList(List<Voucher> voucherList) {
        System.out.println();
        System.out.println("------------< voucherList >--------------");
        for (Voucher voucher : voucherList) {
            System.out.println(voucher.getVoucherId());
        }
        System.out.println("-----------------------------------------");
    }

    public void printVoucherListEmpty() {
        System.out.println();
        System.out.println("------------< voucherList >--------------");
        System.out.println("               ~ EMPTY ~                 ");
        System.out.println("-----------------------------------------");
    }

    public void printInputSign() {
        System.out.print(">> ");
    }
}
