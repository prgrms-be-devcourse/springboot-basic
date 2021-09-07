package prgms.springbasic.consol;

import prgms.springbasic.customer.Customer;
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

    public void printCustomerCommandList(){
        System.out.println("=== Customer Blacklist Program ===");
        System.out.println("Type create to create a new customer.");
        System.out.println("Type find to find blacklist customer.");
        System.out.println("Type list to list all customer.");
        System.out.println("Type exit to exit the program.");
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

    public void customerNameInput(){
        System.out.print("Enter the customer's Name : ");
    }

    public void printVoucherList(List<Voucher> voucherList) {
        System.out.println();
        System.out.println("------------< voucherList >--------------");
        for (Voucher voucher : voucherList) {
            System.out.println(voucher.toString());
        }
        System.out.println("-----------------------------------------");
    }

    public void printCustomerList(List<Customer> customerList) {
        System.out.println();
        System.out.println("------- <Customer Blacklist> -------");
        for (Customer customer : customerList) {
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------");
    }

    public void printVoucherListEmpty() {
        System.out.println();
        System.out.println("------------< voucherList >--------------");
        System.out.println("               ~ EMPTY ~                 ");
        System.out.println("-----------------------------------------");
    }

    public void printVoucherCreateSuccess(Voucher voucher) {
        System.out.println("\n" + voucher.getClass().getSimpleName() + " create success!!");
        System.out.println("voucher Id : " + voucher.getVoucherId());
    }

    public void printInputSign() {
        System.out.print(">> ");
    }
}
