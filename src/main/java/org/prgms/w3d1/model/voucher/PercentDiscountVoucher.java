package org.prgms.w3d1.model.voucher;

import java.io.Serializable;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {
    private final UUID voucherId;
    private final long percent;
    private UUID voucherWalletId;

    private PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public static PercentDiscountVoucher of(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }

    public static PercentDiscountVoucher of(UUID voucherId, long percent, UUID voucherWalletId) {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, percent);
        percentDiscountVoucher.setVoucherWalletId(voucherWalletId);
        return percentDiscountVoucher;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherValue() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public UUID getVoucherWalletId() {
        return voucherWalletId;
    }

    @Override
    public void setVoucherWalletId(UUID voucherWalletId) {
        this.voucherWalletId = voucherWalletId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PercentDiscountVoucher that = (PercentDiscountVoucher) o;

        if (percent != that.percent) return false;
        return voucherId != null ? voucherId.equals(that.voucherId) : that.voucherId == null;
    }

    @Override
    public int hashCode() {
        int result = voucherId != null ? voucherId.hashCode() : 0;
        result = 31 * result + (int) (percent ^ (percent >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
            "voucherId=" + voucherId +
            ", percent=" + percent +
            '}';
    }
}
