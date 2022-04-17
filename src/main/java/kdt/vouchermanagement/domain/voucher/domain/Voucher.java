package kdt.vouchermanagement.domain.voucher.domain;

import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    protected boolean validateDiscountValue(String discountValue) {
        return false;
    }
}
