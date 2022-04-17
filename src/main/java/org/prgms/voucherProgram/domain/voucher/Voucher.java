package org.prgms.voucherProgram.domain.voucher;

import java.io.Serializable;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    protected final UUID voucherId;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    abstract long discount(long beforeDiscount);

    public UUID getVoucherId() {
        return voucherId;
    }
}
