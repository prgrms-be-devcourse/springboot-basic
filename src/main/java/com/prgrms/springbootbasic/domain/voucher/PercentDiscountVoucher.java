package com.prgrms.springbootbasic.domain.voucher;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;

    public PercentDiscountVoucher(long discount) {
        this.voucherId = UUID.randomUUID();
        this.discount = discount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }
}
