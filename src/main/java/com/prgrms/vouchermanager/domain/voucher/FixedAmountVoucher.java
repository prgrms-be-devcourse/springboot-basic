package com.prgrms.vouchermanager.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(long discount) {
        super(discount);
    }

    public FixedAmountVoucher(UUID id, long discount) {
        super(id, discount);
    }

    @Override
    public long discount(long beforeDiscount) {
        long afterDiscount = beforeDiscount - discount;
        return afterDiscount < 0 ? 0 : afterDiscount;
    }
    public String toString() {
        return """
                Id : %s
                Discount Type : %s
                Discount amount : %d
                """.formatted(id, "fixed discount", discount);
    }
}
