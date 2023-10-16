package com.prgrms.vouchermanager.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PercentAmountVoucher implements Voucher {

    private UUID id = UUID.randomUUID();
    private final long percent;

    public PercentAmountVoucher(long percent) {
        this.percent = percent;
    }

    public PercentAmountVoucher(UUID id, long percent) {
        this.id = id;
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
