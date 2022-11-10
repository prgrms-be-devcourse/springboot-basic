package com.program.voucher.service;

import com.program.voucher.model.FixedAmountVoucher;
import com.program.voucher.model.PercentDiscountVoucher;
import com.program.voucher.model.Voucher;
import com.program.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherProduction {
    public Voucher createVoucher(String stringVoucherType, UUID voucherId, int discount){

        VoucherType voucherType = VoucherType.of(stringVoucherType);
        System.out.println(voucherType);
        Voucher newVoucher = switch (voucherType){
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, discount);
            case FIXED_AMOUNT_DISCOUNT ->  new FixedAmountVoucher(voucherId, discount);

        };
        return newVoucher;
    }
}
