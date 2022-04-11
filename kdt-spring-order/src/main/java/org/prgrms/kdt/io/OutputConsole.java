package org.prgrms.kdt.io;

public class OutputConsole implements Output{
    @Override
    public void init() {
        String message = (
                """
                \n=== Voucher Program ===
                Type 'exit' to exit the program.
                Type 'create' to create a new voucher.
                Type 'list' to list all vouchers.
                """
        ); System.out.print(message);
    }

    @Override
    public void printInvalidCmd(String msg) { System.out.println(msg + "Please try again."); }

    @Override
    public void selectVoucherType() {
        String message = ("""
                \nSelect voucher type.
                Type 'fixed' to create FixedAmountVoucher.
                Type 'percent' to create PercentDiscountVoucher.
                """
        ); System.out.print(message);
    }

    @Override
    public void setVoucherDiscount() {
        String message = ("""
                \nType discount rate of voucher.
                Example : if you typed 10,
                * FixedAmountVoucher : -10$ discount
                * PercentDiscountVoucher : 10% discount
                """
        ); System.out.print(message);
    }

    @Override
    public void printInvalidNum(String msg) {

    }



    @Override
    public void printInvalidVoucherType(String msg) {

    }

}
