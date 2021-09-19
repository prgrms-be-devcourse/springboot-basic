package org.programmers.voucher;

import java.util.UUID;

public class VoucherResponse {

    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;
    private UUID ownerId;

    public VoucherResponse(UUID voucherId, long discountValue, VoucherType voucherType, UUID ownerId) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        this.ownerId = ownerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

}
