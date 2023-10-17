package com.prgrms.vouchermanager.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PercentAmountVoucher implements Voucher {

    private final UUID id;
    private final long percent;

    public PercentAmountVoucher(long percent) {
        this.id = UUID.randomUUID();
        this.percent = percent;
    }

    public PercentAmountVoucher(UUID id, long percent) {
        this.id = id;
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {

        long afterDiscount = beforeDiscount - beforeDiscount * (percent / 100);
        return afterDiscount < 0 ? 0 : afterDiscount;
    }

    public String toString() {
        return """
                Id : %s
                Discount Type : %s
                Discount percent : %d
                """.formatted(id, "percent discount", percent);
    }
}
