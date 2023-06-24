package com.prgrms.io;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;

import java.util.List;

public class Output {
    public void viewStartMessage() {
        System.out.println(Message.START.toString());
    }

    public void viewInputError() {
        System.out.println( Message.ERROR.toString());
    }

    public  void viewEndMessage() {
        System.out.println( Message.END.toString());
    }

    public void viewVoucherOption() {
        for(VoucherPolicy voucherPolicy : VoucherPolicy.values()){
            System.out.println(voucherPolicy.toString());
        }
    }
    public static void viewDiscountGuide(VoucherPolicy voucherPolicy) {
        System.out.println(voucherPolicy.getDiscountGuide());
    }

    public static void viewVoucherList(List<Voucher> voucherList) {
        for(Voucher voucher : voucherList){
            System.out.println(voucher.getVoucherId()+": "+voucher.getVoucherDiscount());
        }
    }

    public static void viewCompleteVoucher() {
        System.out.println(Message.COMPLETE_CREATE.toString());
    }

    public static void viewEmptyRepository() {
        System.out.println(Message.EMPTY_REPOSITORY.toString());
    }
}
