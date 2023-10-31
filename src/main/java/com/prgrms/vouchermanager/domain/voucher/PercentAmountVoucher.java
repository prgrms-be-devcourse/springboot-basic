package com.prgrms.vouchermanager.domain.voucher;

import java.util.UUID;

public class PercentAmountVoucher extends Voucher {
    public PercentAmountVoucher(int discount) {
        super(discount);
        super.type = VoucherType.PERCENT;
    }

    public PercentAmountVoucher(UUID id, int discount) {
        super(id, discount);
        super.type = VoucherType.PERCENT;
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
