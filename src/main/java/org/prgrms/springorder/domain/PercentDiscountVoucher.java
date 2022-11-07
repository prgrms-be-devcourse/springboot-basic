package org.prgrms.springorder.domain;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, long amount) {
       super(voucherId, amount);
    }

    @Override
    protected void validateAmount(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("할인율은 0보다 작을 수 없습니다.");
        }

        if (amount > 100) {
            throw new IllegalArgumentException("할인율은 100보다 클 수 없습니다.");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - ((beforeDiscount * getAmount()) / 100);
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PercentDiscountVoucher that = (PercentDiscountVoucher) o;
        return getVoucherId() == that.getVoucherId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId());
    }

}
