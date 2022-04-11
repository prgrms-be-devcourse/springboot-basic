package org.prgrms.kdt.io;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.HashMap;

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
    public void printVoucherList(HashMap<String, Voucher> voucherRepo) {
        voucherRepo.forEach((key, value) -> { System.out.println(value.toString()); });
    }

    @Override
    public void emptyVoucherList(){
        System.out.println("You don't have any vouchers.");
    }

    @Override
    public void printInvalidCmd(String msg) { System.out.println(msg + "Please try again."); }



}
