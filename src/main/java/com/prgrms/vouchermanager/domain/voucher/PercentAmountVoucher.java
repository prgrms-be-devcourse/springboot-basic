package com.prgrms.vouchermanager.domain.voucher;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(long discount) {
        super(discount);
    }

    public PercentAmountVoucher(UUID id, long discount) {
        super(id, discount);
    }

    @Override
    public long discount(long beforeDiscount) {
        long afterDiscount = beforeDiscount - beforeDiscount * (discount / 100);
        return afterDiscount < 0 ? 0 : afterDiscount;
    }

    @Override
    public String toString() {
        return """
                Id : %s
                Discount Type : %s
                Discount percent : %d
                """.formatted(id, "percent discount", discount);
    }
}
