package org.programmer.kdtspringboot.voucher;

import org.springframework.stereotype.Repository;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{

    private final UUID voucherId;
    private final Long amount;

    public PercentDiscountVoucher(UUID voucherId, Long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getValue() {
        return amount;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount * (amount/100);
    }
}
