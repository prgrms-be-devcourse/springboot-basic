package com.programmers.kdtspringorder.voucher.factory;

import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;

import java.util.UUID;

public class VoucherFactory {

    public Voucher createVoucher(int type){
        if (type == 1) {
            return new FixedAmountVoucher(UUID.randomUUID(), 2000L);
        } else {
            return new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        }
    }
}
