package com.prgrms.springbootbasic.domain;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percentDiscount;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscount() {
        return percentDiscount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }
}
