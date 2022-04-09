package org.prgrms.vouchermanagement.domain;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {

    private final UUID voucherId;
    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {

        return voucherId;
    }

    @Override
    public String toString() {

        return voucherId + " " + percent + " PercentDiscountVoucher";
    }
}
