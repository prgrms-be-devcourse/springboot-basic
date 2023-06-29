package com.example.demo.domain.voucher;

import com.example.demo.util.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final int discountAmount;
    private final VoucherType voucherType = VoucherType.FIXED_AMOUNT_VOUCHER;

    public FixedAmountVoucher(UUID id, int discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }
}
