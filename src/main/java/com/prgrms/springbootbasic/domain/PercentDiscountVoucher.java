package com.prgrms.springbootbasic.domain;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percentDiscount;

    public PercentDiscountVoucher(long percentDiscount) {
        this.voucherId = UUID.randomUUID();
        this.percentDiscount = percentDiscount;
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
