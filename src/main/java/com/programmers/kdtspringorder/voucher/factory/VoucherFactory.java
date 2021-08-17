package com.programmers.kdtspringorder.voucher.factory;

import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(VoucherType voucherType){
        if (voucherType == VoucherType.FIXED) {
            return new FixedAmountVoucher(UUID.randomUUID(), 2000L);
        } else {
            return new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        }
    }
}

