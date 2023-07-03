package com.prgrms.springbootbasic.domain;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class FixedDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;

    public FixedDiscountVoucher(long discount) {
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }
}
