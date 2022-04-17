package org.prgms.voucherProgram.domain.voucher;

import java.io.Serializable;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    protected final UUID voucherId;
    protected UUID customerId;

    protected Voucher(UUID voucherId, UUID customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
        this.customerId = null;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
    
    public abstract long discount(long beforeDiscount);

    public abstract void changeDiscountValue(Long discountValue);

    public abstract int getType();

    public abstract long getDiscountValue();
}
