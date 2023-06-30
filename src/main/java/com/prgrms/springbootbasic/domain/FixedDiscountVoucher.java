package com.prgrms.springbootbasic.domain;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FixedDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long fixedDiscount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return fixedDiscount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }
}
