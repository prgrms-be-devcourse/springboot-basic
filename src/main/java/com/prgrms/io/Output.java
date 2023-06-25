package com.prgrms.io;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;

import java.util.List;

public class Output {
    public void viewStartMessage() {
        System.out.println(Message.START.getMessage());
    }

    public void viewInputError() {
        System.out.println(Message.ERROR.getMessage());
    }

    public void viewEndMessage() {
        System.out.println(Message.END.getMessage());
    }

    public void viewVoucherOption() {
        for (VoucherPolicy voucherPolicy : VoucherPolicy.values()) {
            System.out.println(voucherPolicy.voucherPolicyOptionGuid());
        }
    }

    public static void viewDiscountGuide(VoucherPolicy voucherPolicy) {
        System.out.println(voucherPolicy.discountGuide());
    }

    public static void viewVoucherList(List<VoucherResponse> voucherList) {
        for (VoucherResponse voucher : voucherList) {
            System.out.println(voucher.getVoucherType() + " : " + voucher.getDiscount());
        }
    }

    public static void viewCompleteVoucher() {
        System.out.println(Message.COMPLETE_CREATE.toString());
    }

    public static void viewEmptyRepository() {
        System.out.println(Message.EMPTY_REPOSITORY.toString());
    }
}
