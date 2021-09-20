package org.prgrms.kdt.commadLineApp;

public class InPutView {

    public static void startProgram(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type join to create new customer");
        System.out.println("Type assignVoucher to give voucher to customer");
        System.out.println("Type deleteVoucher to delete vouchers.");
        System.out.println("Type customerList to show customerList");
        System.out.println("Type customerVoucher to show customer's voucher");
        System.out.println("Type whoHaveVoucher to show customer who have Voucher");
        System.out.println("Type blacklist to show blacklist");
    }

    public static void inputCustomerName(){
        System.out.print("Input your Name : ");
    }

    public static void inputCustomerEmail(){
        System.out.print("Input your Email : ");
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
    public static void assignCustomerEmail() {
        System.out.print("Input Customer Email : ");
    }

    public static void inputVoucherId(){
        System.out.print("Input Voucher Id : ");
    }
}
