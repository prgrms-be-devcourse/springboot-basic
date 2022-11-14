package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.domain.voucher.VoucherType.*;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Long voucherId, Long discountValue) {
        super(voucherId, discountValue, FIXED_AMOUNT);
    }

}
