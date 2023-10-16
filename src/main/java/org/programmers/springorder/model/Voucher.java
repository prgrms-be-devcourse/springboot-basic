package org.programmers.springorder.model;

import java.util.Objects;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, long discountValue, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
    }

    public long getCalculate(long beforeDiscount){
        return this.voucherType.calculate(beforeDiscount, this.discountValue);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return discountValue == voucher.discountValue && Objects.equals(voucherId, voucher.voucherId) && voucherType == voucher.voucherType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountValue, voucherType);
    }

}
