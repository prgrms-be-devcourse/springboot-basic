package org.prgms.voucher.voucher;

import lombok.ToString;

import java.util.UUID;

@ToString
public class PercentDiscountVoucher implements Voucher {

    private final long percentage;
    private final UUID id;

    public PercentDiscountVoucher(long percentage, UUID id) {
        this.percentage = percentage;
        this.id = id;
    }

    @Override
    public long discount(long price) {
        return price * (100 - percentage) / 100;
    }

}
