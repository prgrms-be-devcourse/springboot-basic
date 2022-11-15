package org.prgrms.voucherapplication.voucher.entity;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher{

    public FixedAmountVoucher(UUID uuid, int discount) {
        super(uuid, discount);
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "uuid=" + uuid +
                ", discount=" + discount +
                "}\n";
    }
}
