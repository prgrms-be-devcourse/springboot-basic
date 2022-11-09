package org.prgrms.springorder.domain;

import java.util.Objects;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    @Override
    protected void validateAmount(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("할인금액은 0보다 작을 수 없습니다.");
        }
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountAmount = beforeDiscount - this.getAmount();

        return discountAmount < 0 ? 0 : discountAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FixedAmountVoucher that = (FixedAmountVoucher) o;
        return Objects.equals(this.getVoucherId(),that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId());
    }

}