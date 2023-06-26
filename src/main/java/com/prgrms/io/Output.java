package com.prgrms.io;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;

import java.util.List;

public class Output {
    public static void viewStartMessage() {
        System.out.println(Message.START.getMessage());
    }

    public static void viewInputError() {
        System.out.println(Message.ERROR.getMessage());
    }

    public static void viewEndMessage() {
        System.out.println(Message.END.getMessage());
    }

    public static void viewVoucherOption() {
        for (VoucherPolicy voucherPolicy : VoucherPolicy.values()) {
            System.out.println(voucherPolicy.voucherPolicyOptionGuid());
        }
    }

    public static void viewDiscountGuide(VoucherPolicy voucherPolicy) {
        System.out.println(voucherPolicy.discountGuide());
    }

    public static void viewVoucherList(List<VoucherResponse> voucherList) {
        for (VoucherResponse voucherResponse : voucherList) {
            System.out.println(voucherResponse.getVoucherPolicy().name() + " : "
                    + voucherResponse.getDiscount().getDiscount()
                    + voucherResponse.getVoucherPolicy().getUnit());
        }
    }

    public static void viewCompleteVoucher() {
        System.out.println(Message.COMPLETE_CREATE.getMessage());
    }

    public static void viewEmptyRepository() {
        System.out.println(Message.EMPTY_REPOSITORY.getMessage());
    }
}
