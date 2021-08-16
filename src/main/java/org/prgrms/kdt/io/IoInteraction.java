package org.prgrms.kdt.io;

public class IoInteraction {

    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
    }

    public void exit() {
        System.out.println("exit program");
    }

    public void create() {
        System.out.println("choose the NUMBER of voucher you want to make");
        // 처음에 아래처럼 했는데 그럼 종류가 추가될때마다 하드코딩이 되어야한다고 생각해서 enum클래스를 추가하였습니다.
        System.out.println("1.FixedAmountVoucher, 2.PercentDiscountVoucher");
    }

    public void discountRate() {
        System.out.println("Please enter a discount rate");

    }

    public void fail(String input) {
        System.out.println("'"+input+"'" + " is not a registerd command, See 'help'");
    }

    public void save() {
        System.out.println("create new voucher complete...");
    }
}
