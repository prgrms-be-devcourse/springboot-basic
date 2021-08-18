package org.prgms;

public class Io {

    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
    }

    public void create(){
        System.out.println("Pick " + VoucherType.voucherTypes());
    }

    public void exit() {
        System.out.println("exit program");
    }

    public void list() {
        System.out.println("list all vouchers");
    }

}
