package prgms.springbasic.io;

import prgms.springbasic.BlackCustomer.BlackCustomer;
import prgms.springbasic.voucher.Voucher;

import java.util.List;

public class Printer {
    public void printCommandList() {
        System.out.println(System.lineSeparator() + "=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        printInputSign();
    }

    public void printBlackCustomerCommandList() {
        System.out.println(System.lineSeparator() + "=== Customer Blacklist Program ===");
        System.out.println("Type save to save a new black customer.");
        System.out.println("Type find to find blacklist customer.");
        System.out.println("Type list to list all black customer.");
        System.out.println("Type exit to exit the program.");
        printInputSign();
    }

    public void printVoucherTypeList() {
        System.out.println(System.lineSeparator() + "=== Enter the number of voucher type ===");
        System.out.println("1) FixedAmountVoucher");
        System.out.println("2) PercentDiscountVoucher");
        printInputSign();
    }

    public void printDiscountValueInputInfo() {
        System.out.println(System.lineSeparator() + "=== Enter the discount amount ===");
        printInputSign();
    }

    public void customerNameInput() {
        System.out.print(System.lineSeparator() + "Enter the customer's Name : ");
    }

    public void printVoucherList(List<Voucher> voucherList) {
        System.out.println(System.lineSeparator() + "------------< voucherList >--------------");
        for (Voucher voucher : voucherList) {
            System.out.println(voucher.toString());
        }
        System.out.println("-----------------------------------------");
    }

    public void printBlackCustomerList(List<BlackCustomer> blackCustomerList) {
        System.out.println(System.lineSeparator() + "-------< Customer Blacklist >-------");
        for (BlackCustomer blackCustomer : blackCustomerList) {
            System.out.println(blackCustomer.toString());
        }
        System.out.println("-------------------------------------");
    }

    public void printVoucherListEmpty() {
        System.out.println(System.lineSeparator() + "------------< voucherList >--------------");
        System.out.println("               ~ EMPTY ~                 ");
        System.out.println("-----------------------------------------");
    }

    public void printBlackCustomerListEmpty() {
        System.out.println(System.lineSeparator() + "------------< Customer Blacklist >--------------");
        System.out.println("               ~ EMPTY ~                 ");
        System.out.println("-----------------------------------------");
    }

    public void printVoucherCreateSuccess(Voucher voucher) {
        System.out.println(System.lineSeparator() + voucher.getClass().getSimpleName() + " create success!!");
        System.out.println("voucher ID : " + voucher.getVoucherId());
    }

    public void printBlackCustomerSaveSuccess(BlackCustomer blackCustomer) {
        System.out.println(System.lineSeparator() + blackCustomer.getClass().getSimpleName() + " save success!!");
        System.out.println("Black customer ID : " + blackCustomer.getCustomerId());
    }

    public void printBlackCustomerIsNotPresent(String name){
        System.out.println(System.lineSeparator() + name + " is not BlackCustomer!!");
    }

    public void printInputSign() {
        System.out.print(">> ");
    }
}
