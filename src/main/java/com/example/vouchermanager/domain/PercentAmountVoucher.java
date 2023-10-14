package com.example.vouchermanager.domain;

import java.util.UUID;

public class PercentAmountVoucher implements Voucher {

    private final UUID id = UUID.randomUUID();
    private final long percent;

    public PercentAmountVoucher(long percent) {
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * (percent / 100);
    }

    public String toString() {
        return """
                Id : %s
                Discount Type : %s
                Discount percent : %d
                """.formatted(id, "percent discount", percent);
    }
}
