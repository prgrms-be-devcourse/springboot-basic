package org.prgms.w3d1.model.voucher;

import java.io.Serializable;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long amount;
    private UUID customerId;

    private FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, long amount, UUID customerId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
    }

    public static FixedAmountVoucher of(UUID voucherId, long amount){
        return new FixedAmountVoucher(voucherId, amount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherValue() {
        return amount;
    }



    @Override
    public long discount(long beforeDiscount){
        return beforeDiscount - amount;
    }

    @Override
    public UUID getCustomerId() {
        return this.customerId;
    }

    @Override
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedAmountVoucher that = (FixedAmountVoucher) o;

        if (amount != that.amount) return false;
        return voucherId != null ? voucherId.equals(that.voucherId) : that.voucherId == null;
    }

    @Override
    public int hashCode() {
        int result = voucherId != null ? voucherId.hashCode() : 0;
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
