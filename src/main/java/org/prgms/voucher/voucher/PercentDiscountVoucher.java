package org.prgms.voucher.voucher;

import lombok.ToString;

import java.util.UUID;

@ToString
public class PercentDiscountVoucher extends Voucher {

    private final long percentage;

    public PercentDiscountVoucher(long percentage, UUID id) {
        super(id);
        this.percentage = percentage;
    }

    @Override
    public long discount(long price) {
        return price * (100 - percentage) / 100;
    }

}
