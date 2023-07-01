package com.prgrms.springbootbasic.domain;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FixedDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long fixedDiscount;

    public FixedDiscountVoucher(long fixedDiscount) {
        this.voucherId = UUID.randomUUID();
        this.fixedDiscount = fixedDiscount;
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
