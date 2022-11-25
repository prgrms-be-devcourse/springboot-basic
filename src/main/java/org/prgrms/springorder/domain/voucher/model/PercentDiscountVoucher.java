package org.prgrms.springorder.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.model.Customer;

public class PercentDiscountVoucher extends Voucher {

    private static final int MINIMUM_PERCENT_AMOUNT = 0;
    private static final int MAXIMUM_PERCENT_AMOUNT = 100;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
    }

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        super(voucherId, amount, createdAt);
    }

    @Override
    protected void validateAmount(long amount) {
        if (amount < MINIMUM_PERCENT_AMOUNT) {
            throw new IllegalArgumentException("할인율은 0보다 작을 수 없습니다.");
        }

        if (amount > MAXIMUM_PERCENT_AMOUNT) {
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
        return Objects.equals(this.getVoucherId(), that.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId());
    }

}
