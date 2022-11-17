package com.program.commandLine.voucher;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, int discount){

        Voucher newVoucher = switch (voucherType){
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, discount);
            case FIXED_AMOUNT_DISCOUNT ->  new FixedAmountVoucher(voucherId, discount);
        };
        return newVoucher;
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, int discount, UUID AssignedCustomerId, boolean using){

        Voucher newVoucher = switch (voucherType){
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId,discount, AssignedCustomerId, using);
            case FIXED_AMOUNT_DISCOUNT ->  new FixedAmountVoucher(voucherId, discount, AssignedCustomerId, using);
        };
        return newVoucher;
    }
}
