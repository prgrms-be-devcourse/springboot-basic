package com.prgms.vouchermanager.domain.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;

    private final long percent;

    private final VoucherType type;

    public PercentDiscountVoucher(UUID id, long percent) {
        this.id = id;
        this.percent = percent;
        type = VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT;
    }



    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", percent=" + percent +
                ", type=" + type +
                '}';
    }
}
