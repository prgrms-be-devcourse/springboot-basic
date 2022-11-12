package org.prgrms.voucherapplication.entity;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    public PercentDiscountVoucher(UUID uuid, int percent) {
        super(uuid, percent);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "uuid=" + uuid +
                ", discount=" + discount +
                '}';
    }
}
